package com.example.ws;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.MessageSenderInterceptor;
import org.apache.cxf.io.AbstractWrappedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.rm.RMContextUtils;

public class MessageLossSimulator extends AbstractPhaseInterceptor<Message> {

	private static final Logger LOG = Logger
			.getLogger(MessageLossSimulator.class.getName());
	
	private int appMessageCount;

	public MessageLossSimulator() {
		super(Phase.PREPARE_SEND);
		addBefore(MessageSenderInterceptor.class.getName());
	}

	public void handleMessage(Message message) throws Fault {
		AddressingProperties maps = RMContextUtils.retrieveMAPs(message, false,
				true);
		
		String action = null;
		if (maps != null && null != maps.getAction()) {
			action = maps.getAction().getValue();
		}
		if (RMContextUtils.isRMProtocolMessage(action)) {
			return;
		}
		appMessageCount++;
		// do not discard odd-numbered messages
		if (0 != (appMessageCount % 2)) {
			return;
		}

		// discard even-numbered message
		InterceptorChain chain = message.getInterceptorChain();
		ListIterator<Interceptor<? extends Message>> it = chain.getIterator();
		while (it.hasNext()) {
			PhaseInterceptor<? extends Message> pi = (PhaseInterceptor<? extends Message>) it
					.next();
			if (MessageSenderInterceptor.class.getName().equals(pi.getId())) {
				chain.remove(pi);
				LOG.info("Removed MessageSenderInterceptor from interceptor chain.");
				break;
			}
		}

		message.setContent(OutputStream.class, new WrappedOutputStream(message));

		message.getInterceptorChain()
				.add(new AbstractPhaseInterceptor<Message>(
						Phase.PREPARE_SEND_ENDING) {

					public void handleMessage(Message message) throws Fault {
						try {
							message.getContent(OutputStream.class).close();
						} catch (IOException e) {
							throw new Fault(e);
						}
					}

				});
	}

	private class WrappedOutputStream extends AbstractWrappedOutputStream {

		private Message outMessage;

		public WrappedOutputStream(Message m) {
			this.outMessage = m;
		}

		@Override
		protected void onFirstWrite() throws IOException {
			if (LOG.isLoggable(Level.FINE)) {
				BigInteger nr = RMContextUtils
						.retrieveRMProperties(outMessage, true).getSequence()
						.getMessageNumber();
				LOG.fine("Losing message " + nr);
			}
			wrappedStream = new DummyOutputStream();
		}
	}

	private class DummyOutputStream extends OutputStream {

		@Override
		public void write(int b) throws IOException {
		}
	}
}
