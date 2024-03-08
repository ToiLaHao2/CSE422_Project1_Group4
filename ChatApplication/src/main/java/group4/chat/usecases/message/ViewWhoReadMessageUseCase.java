package group4.chat.usecases.message;


import java.util.Set;


import group4.chat.message.Conversation;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ViewWhoReadMessageUseCase
		extends UseCase<ViewWhoReadMessageUseCase.InputValues, ViewWhoReadMessageUseCase.OutputValues> {
	private DataStorage dataStorage;

	public ViewWhoReadMessageUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		Conversation conversation = dataStorage.getConversation(input.conversationId);
		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED, "Conversation not found");
		}
		Set<String> usersWhoReadMessage = conversation.getUsersWhoReadMessage(input.messageId);

		return new OutputValues(ResultCodes.SUCCESS, "Users who read your message: " + usersWhoReadMessage);
	}

	public static class InputValues {
		private final String conversationId;
		private final int messageId;

		public InputValues(String conversationId, int messageId) {
			this.conversationId = conversationId;
			this.messageId = messageId;
		}

		public String getConversationId() {
			return conversationId;
		}

		public int getMessageId() {
			return messageId;
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
