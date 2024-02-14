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
	private String _attachment;

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

	public Message(int _messageId, String _sender, String _receiver, String _content) {
		this._messageId = _messageId;
		this._sender = _sender;
		this._receiver = _receiver;
		this._content = _content;
	}

	public Message(int _messageId, String _sender, String _receiver, String _content, String _attachment) {
		this._messageId = _messageId;
		this._sender = _sender;
		this._receiver = _receiver;
		this._content = _content;
		this._attachment = _attachment;
	}

	public ArrayList<String> get_attachments() {
		return _attachments;
	}
	

	public void set_content(String _content) {
		this._content = _content;
	}
	

	public String get_content() {
		return _content;
	}

	public int get_messageId() {
		return _messageId;
	}

	@Override
	public String toString() {
		return _messageId + " " + _sender + " " + _receiver + " " + _timestamp + " " + _content + " " + _attachments;
	}
	

}
