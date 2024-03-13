package group4.chat.usecases.message;

import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

import java.util.List;

public class ViewNLatestMessagesUseCase
		extends UseCase<ViewNLatestMessagesUseCase.InputValues, ViewNLatestMessagesUseCase.OutputValues> {

	private DataStorage _dataStorage;

	public ViewNLatestMessagesUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		Conversation conversation = _dataStorage.getConversation(input.getConversationId());
		
		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED, "Conversation not found");
		}

		List<Message> latestMessages = conversation.get_messages();
		int totalMessages = latestMessages.size();

		// Ensure N is within bounds
		int n = Math.min(input.getN(), totalMessages);

		// Get the latest N messages
		List<Message> resultMessages = latestMessages.subList(totalMessages - n, totalMessages);

		return new OutputValues(ResultCodes.SUCCESS, resultMessages);
	}

	public static class InputValues {
		private String _conversationId;
		private int _nLatestMessages;

		public InputValues(String conversationId, int n) {
			this._conversationId = conversationId;
			this._nLatestMessages = n;
		}

		public String getConversationId() {
			return _conversationId;
		}

		public int getN() {
			return _nLatestMessages;
		}
	}

	public static class OutputValues {
		private int _resultCode;
		private List<Message> _messages;
		private String _message;

		public OutputValues(int resultCode, List<Message> messages) {
			this._resultCode = resultCode;
			this._messages = messages;
		}

		public OutputValues(int resultCode, String message) {
			_message = message;
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
		}

		public List<Message> getMessages() {
			return _messages;
		}

		public String getMessage() {
			return _message;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}
}
