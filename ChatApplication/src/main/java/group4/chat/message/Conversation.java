package group4.chat.message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
	private String _user1;
	private String _user2;
	private String _group;
	private ArrayList<Message> _messages;
    private String conversationId;
    private List<Message> messageHistory;

	public Conversation(String user1, String user2, String group) {
		this._messages = new ArrayList<>();

		if (user1 != null && user2 != null) {
			// Conversation between two users
			this._user1 = user1;
			this._user2 = user2;
		} else if (group != null) {
			// Conversation within a group
			this._group = group;
		} else {
			throw new IllegalArgumentException("Invalid conversation");
		}
	}
    public Conversation() {
        this._messages=new ArrayList<>();
    }
    public Conversation(String conversationId) {
        this.conversationId = conversationId;
        this.messageHistory = new ArrayList<>();
    }
   
	
	public void addMessage(String sender, String receiver, String content, ArrayList<String> attachments) {
        if (attachments == null) {
            attachments = new ArrayList<>();
        }
        int messageId = _messages.size() + 1;
        Message message = new Message(messageId, sender, receiver, content, attachments);
        _messages.add(message);
    }
    public void addMessage(Message message) {
        messageHistory.add(message);
    }
	
    public void displayMessages() {
        for (Message message : _messages) {
            System.out.println(message);
        }
    }
    public void displayMessageHistory() {
        for (Message message : messageHistory) {
            System.out.println(message);
        }
    }
	public Message getMessageById(int messageId) {
        for (Message message : _messages) {
            if (message.get_messageId() == messageId) {
                return message;
            }
        }
        return null;
    }
    
    public void deleteMessage(Message message) {
        _messages.remove(message);
    }
    public ArrayList<Message> get_messages() {
        return _messages;
    }
     public List<Message> getLatestMessages(int k, long timestamp) {
        List<Message> latestMessages = new ArrayList<>();
        LocalDateTime stampTime = LocalDateTime.now().minusSeconds(timestamp);

        for (int i = _messages.size() - 1; i >= 0; i--) {
            Message message = _messages.get(i);
            if (message.getTimestamp().isAfter(stampTime)) {
                latestMessages.add(message);
            }
            if (latestMessages.size() >= k) {
                break;
            }
        }
        return latestMessages;
    }

}
