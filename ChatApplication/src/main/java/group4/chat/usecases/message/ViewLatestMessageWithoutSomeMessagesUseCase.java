package group4.chat.usecases.message;

import java.util.List;

import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ViewLatestMessageWithoutSomeMessagesUseCase extends
		UseCase<ViewLatestMessageWithoutSomeMessagesUseCase.InputValues, ViewLatestMessageWithoutSomeMessagesUseCase.OutputValues> {
	private DataStorage dataStorage;

	public ViewLatestMessageWithoutSomeMessagesUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		Conversation conversation = dataStorage.getConversation(input.getConversationId());
		
		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED, "Conversation not found");
		}

		List<Message> latestMessages = conversation.get_messages();
		
		int totalMessages = latestMessages.size();
		int k = Math.min(input.getK(), totalMessages);
		int m = Math.min(input.getM(), totalMessages);
		
		List<Message> resultMessages = latestMessages.subList(totalMessages - k, totalMessages);
		
		if (m > 0 && m <= k) {
			resultMessages.subList(k - m, k).clear();
		}
		
		return new OutputValues(ResultCodes.SUCCESS, resultMessages);
	}

	public static class InputValues {
		private String conversationId;
		private int k;
		private int m;

		public InputValues(String conversationId, int k, int m) {
			this.conversationId = conversationId;
			this.k = k;
			this.m = m;
		}

		public String getConversationId() {
			return conversationId;
		}

		public int getK() {
			return k;
		}

		public int getM() {
			return m;
		}

	}

	public static class OutputValues {
		private int _resultCode;
		private String _message;
		private List<Message> _messages;

		public OutputValues(int resultCode, String message) {
			_message = message;
			_resultCode = resultCode;
		}

		public OutputValues(int resultCode, List<Message> messages) {
			this._resultCode = resultCode;
			this._messages = messages;
		}

		public int getResultCode() {
			return _resultCode;
		}

		public String getMessage() {
			return _message;
		}

		public List<Message> getMessages() {
			return _messages;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}
