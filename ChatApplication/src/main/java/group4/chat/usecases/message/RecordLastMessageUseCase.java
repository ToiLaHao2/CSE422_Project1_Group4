package group4.chat.usecases.message;

import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RecordLastMessageUseCase
		extends UseCase<RecordLastMessageUseCase.InputValues, RecordLastMessageUseCase.OutputValues> {
	private DataStorage dataStorage;

	public RecordLastMessageUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		String userId = input.getUserId();
		String conversationId = input.getConversationId();
		Conversation conversation = dataStorage.getConversation(conversationId);
		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED, "Conversation not found");
		}
		Message lastMessage = conversation.get_messages().get(conversation.get_messages().size() - 1);
		dataStorage.updateLastReadMessage(userId, conversationId, lastMessage);

		return new OutputValues(ResultCodes.SUCCESS, "Last message recorded successfully");
	}

	public static class InputValues {
		private String userId;
		private String conversationId;

		public InputValues(String userId, String conversationId) {
			this.userId = userId;
			this.conversationId = conversationId;
		}

		public String getUserId() {
			return userId;
		}

		public String getConversationId() {
			return conversationId;
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
