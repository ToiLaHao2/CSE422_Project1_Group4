package group4.chat.message;

import java.util.ArrayList;
import java.util.Date;

public class Message {
	private int _messageId;
	private String _sender;
	private String _receiver;
	private Date _timestamp;
	private String _content;
	private ArrayList<String> _attachments;

	public Message(int messageId, String sender, String receiver, String content, ArrayList<String> attachments) {
		this._messageId = messageId;
		this._sender = sender;
		this._receiver = receiver;
		this._timestamp = new Date();
		this._content = content;
		if (attachments == null) {
			this._attachments = new ArrayList<>(attachments);
		} else {
			this._attachments = new ArrayList<>();
		}
	}

	@Override
	public String toString() {
		return _messageId + " " + _sender + " " + _receiver + " " + _timestamp + " " + _content + " " + _attachments;
	}

}
