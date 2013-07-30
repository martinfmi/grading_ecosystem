package fmi.uni.grading.shared.beans.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.example.ws.beans.IMessage;
import com.example.ws.beans.Message;

public class MessageAdapter extends XmlAdapter<Message, IMessage> {
	public Message marshal(IMessage message) throws Exception {
		if (message instanceof Message) {
			return (Message) message;
		}

		Message result = new Message();
		result.setText(message.getText());
		return result;
	}

	public Message unmarshal(Message message) throws Exception {
		return message;
	}
}
