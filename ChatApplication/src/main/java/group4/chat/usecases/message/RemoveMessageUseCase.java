package group4.chat.usecases.message;

import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RemoveMessageUseCase extends UseCase<RemoveMessageUseCase.InputValues, RemoveMessageUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public RemoveMessageUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		String userId = input.getUserId();
		String conversationId = input.getConversationId();

		int messageId = input.getMessageId();
		Conversation conversation = _dataStorage.getConversation(conversationId);

		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED);
		}
		Message message = conversation.getMessageById(messageId);
		if (message == null) {
			return new OutputValues(ResultCodes.FAILED);
		}

		conversation.deleteMessage(message);
		_dataStorage.updateConversation(conversation);
		return new OutputValues(ResultCodes.SUCCESS);

	}

	public static class InputValues {
		private final String _userId;
		private final String _conversationId;
		private final int _messageId;

		public InputValues(String userId, String conversationId, int messageId) {
			this._userId = userId;
			this._conversationId = conversationId;
			this._messageId = messageId;
		}

		public String getUserId() {
			return _userId;
		}

		public String getConversationId() {
			return _conversationId;
		}

		public int getMessageId() {
			return _messageId;
		}

	}

	public static class OutputValues {
		private final int _resultCode;

		public OutputValues(int resultCode) {
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}
