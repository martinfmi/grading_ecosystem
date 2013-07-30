package fmi.uni.grading.shared.beans;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.example.ws.beans.adapters.MessageAdapter;

@XmlJavaTypeAdapter(value = MessageAdapter.class)
public interface IMessage {

	/**
	 * Retrieves the message text.
	 */
	public String getText();

	/**
	 * Retrieves the message author.
	 */
	public String getAuthor();

	/**
	 * The text of the message.
	 */
	public void setText(String text);

	/**
	 * The author of the message.
	 */
	public void setAuthor(String author);

}
