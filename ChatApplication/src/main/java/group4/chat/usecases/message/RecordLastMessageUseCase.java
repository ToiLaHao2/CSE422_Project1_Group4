package group4.chat.usecases.message;

import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RecordLastMessageUseCase
		extends UseCase<RecordLastMessageUseCase.InputValues, RecordLastMessageUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public RecordLastMessageUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		String userId = input.getUserId();
		String conversationId = input.getConversationId();

		Conversation conversation = _dataStorage.getConversation(conversationId);

		if (conversation == null) {
			return new OutputValues(ResultCodes.FAILED);
		}
		Message lastMessage = conversation.get_messages().get(conversation.get_messages().size() - 1);
		_dataStorage.updateLastReadMessage(userId, conversationId, lastMessage);

		return new OutputValues(ResultCodes.SUCCESS);
	}

	public static class InputValues {
		private String _userId;
		private String _conversationId;

		public InputValues(String userId, String conversationId) {
			this._userId = userId;
			this._conversationId = conversationId;
		}

		public String getUserId() {
			return _userId;
		}

		public String getConversationId() {
			return _conversationId;
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
