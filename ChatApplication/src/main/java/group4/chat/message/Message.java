package group4.chat.message;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Message {
	private int _messageId;
	private String _sender;
	private String _receiver;
	private LocalDateTime _timestamp;
	private String _content;
	private ArrayList<String> _attachments;
	private String _attachment;

	public Message(int messageId, String sender, String receiver, String content, ArrayList<String> attachments) {
		this._messageId = messageId;
		this._sender = sender;
		this._receiver = receiver;
		this._timestamp = LocalDateTime.now();
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

	public int get_messageId() {
		return _messageId;
	}

	public void setTimeStamp() {
		this._timestamp = 
	}

	@Override
	public String toString() {
		return _messageId + " " + _sender + " " + _receiver + " " + _timestamp + " " + _content + " " + _attachments;
	}

}
