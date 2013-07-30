package fmi.uni.grading.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.ValidationEventCollector;

/**
 * Utility for marshalling/unmarshalling XML content using JAXB beans.
 * 
 * @author Martin Toshev
 */
public class JaxbManager {

	// public static final URL SCHEMA_URL = JaxbManager.class
	// .getResource("lookandfeel.xsd");
	private static final Logger LOGGER = Logger.getLogger(JaxbManager.class
			.getCanonicalName());

	/**
	 * Reads the configuration.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T read(Class<T> docClass, InputStream inputStream)
			throws JAXBException {

		ValidationEventCollector vec = null;
		try {

			String packageName = docClass.getPackage().getName();
			JAXBContext jc = JAXBContext.newInstance(packageName);
			Unmarshaller unmarshaller = jc.createUnmarshaller();

			// create a schema instance used for the validation of the XML
			// configuration input stream
			// Schema mySchema = null;
			// SchemaFactory sf = SchemaFactory
			// .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			//
			// TODO: provide schema validation
			// try {
			// mySchema = sf.newSchema(SCHEMA_URL);
			// } catch (SAXException saxe) {
			// LOGGER.log(Level.WARNING,
			// "Failed to read/parse the XSD schema of the configuration."
			// + "Skipping validation.", saxe);
			// }
			//
			// unmarshaller.setSchema(mySchema);
			T result = (T) unmarshaller.unmarshal(inputStream);

			// setting a validation event collector for collecting validation
			// errors
			vec = new ValidationEventCollector();
			unmarshaller.setEventHandler(vec);

			return result;
		} finally {
			if (vec != null && vec.hasEvents()) {
				for (ValidationEvent ve : vec.getEvents()) {
					String msg = ve.getMessage();
					ValidationEventLocator vel = ve.getLocator();
					int line = vel.getLineNumber();
					int column = vel.getColumnNumber();
					LOGGER.warning("Validation failed on line " + line + "."
							+ column + ": " + msg);
				}
			}
		}
	}

	/**
	 * Writes the configuration based on the configuration root.
	 */
	public static void write(Class<?> clazz, Object document, OutputStream os)
			throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(clazz.getPackage()
				.getName());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(document, os);
	}
}
