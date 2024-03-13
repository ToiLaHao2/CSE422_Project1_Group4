package group4.chat.usecases.message;

import java.util.Set;

import group4.chat.message.Conversation;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ViewWhoReadMessageUseCase
		extends UseCase<ViewWhoReadMessageUseCase.InputValues, ViewWhoReadMessageUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public ViewWhoReadMessageUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		Conversation conversation = _dataStorage.getConversation(input._conversationId);
		
		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED, "Conversation not found");
		}
		
		Set<String> usersWhoReadMessage = conversation.getUsersWhoReadMessage(input._messageId);

		return new OutputValues(ResultCodes.SUCCESS, "Users who read your message: " + usersWhoReadMessage);
	}

	public static class InputValues {
		private final String _conversationId;
		private final int _messageId;

		public InputValues(String conversationId, int messageId) {
			this._conversationId = conversationId;
			this._messageId = messageId;
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
		private final String _message;

		public OutputValues(int resultCode, String message) {
			_message = message;
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
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
