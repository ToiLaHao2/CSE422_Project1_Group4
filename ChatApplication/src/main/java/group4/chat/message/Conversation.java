package group4.chat.message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import group4.chat.domains.User;

public class Conversation {
	private String _user1;
	private String _user2;
	private String _group;
	private ArrayList<Message> _messages;
	private String _conversationId;
	private List<Message> _messageHistory;
	private List<User> _participants;

	private Map<Integer, Set<String>> messageReaders;
	private Map<String, Message> lastReadMessages;

	public String get_user1() {
		return _user1;
	}

	public String get_user2() {
		return _user2;
	}

	public String get_group() {
		return _group;
	}

	public void set_user1(String _user1) {
		this._user1 = _user1;
	}

	public void set_user2(String _user2) {
		this._user2 = _user2;
	}

	public void set_group(String _group) {
		this._group = _group;
	}

	public void set_conversationId(String _conversationId) {
		this._conversationId = _conversationId;
	}

	public Conversation(String user1, String user2, String group) {
		this._messages = new ArrayList<>();
		this.messageReaders = new HashMap<>();

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
		this.lastReadMessages = new HashMap<>();
	}

	public Conversation(String conversationId) {
		this._conversationId = conversationId;
		this._messageHistory = new ArrayList<>();
	}

	public Conversation(String conversationId, List<User> participants) {
		if (conversationId == null || participants == null || participants.size() < 2) {
			throw new IllegalArgumentException("Invalid conversation");
		}

		this._conversationId = conversationId;
		this._participants = new ArrayList<>(participants);
		this._messages = new ArrayList<>();
	}

	public List<User> getParticipants() {
		return _participants;
	}

	public String getParticipantsAsString() {
		StringBuilder participantsString = new StringBuilder();
		for (User participant : _participants) {
			participantsString.append(participant.getId()).append(", ");
		}
		if (participantsString.length() > 0) {
			participantsString.delete(participantsString.length() - 2, participantsString.length());
		}
		return participantsString.toString();
	}

	public String getConversationId() {
		return _conversationId;
	}

	public void addMessage(String sender, String receiver, String content, ArrayList<String> attachments) {
		int messageId = _messages.size() + 1;
		Message message = new Message(messageId, sender, receiver, content, attachments);
		_messages.add(message);
	}

	public void addMessage(Message message) {
		_messageHistory.add(message);
	}

	public void displayMessages() {
		for (Message message : _messages) {
			System.out.println(message);
		}
	}

	public void displayMessageHistory() {
		for (Message message : _messageHistory) {
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

	public List<Message> getLatestMessage(int numberOfMessages, LocalDateTime upToTime) {
		List<Message> allMessages = get_messages();

		Collections.sort(allMessages, (m1, m2) -> m2.get_timestamp().compareTo(m1.get_timestamp()));

		List<Message> latestMessages = new ArrayList<>();
		int count = 0;

		for (Message message : allMessages) {
			if (count < numberOfMessages && message.get_timestamp().isBefore(upToTime)) {
				latestMessages.add(message);
				count++;
			}
		}

		return latestMessages;
	}

	public void markMessageAsRead(int messageId, String userId) {
		messageReaders.computeIfAbsent(messageId, k -> new HashSet<>()).add(userId);
	}

	public Set<String> getUsersWhoReadMessage(int messageId) {
		return messageReaders.getOrDefault(messageId, Collections.emptySet());
	}

	public void setLastReadMessage(String userId, Message lastMessage) {
		lastReadMessages.put(userId, lastMessage);
	}

	public Message getLastReadMessage(String userId) {
		return lastReadMessages.get(userId);
	}

}
