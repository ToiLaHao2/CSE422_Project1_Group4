package group4.chat.usecases.message;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;

public class EditMessageUseCase extends UseCase<EditMessageUseCase.InputValues, EditMessageUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public EditMessageUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		Conversation conversation = _dataStorage.getConversation(input._conversationId);
		
		Message message = conversation.getMessageById(input._messageId);
		message.set_content(input._newContent);

		return new OutputValues(ResultCodes.SUCCESS);
	}

	public static class InputValues {
		private final String _conversationId;
		private final int _messageId;
		private final String _newContent;

		public InputValues(String conversationId, int messageId, String newContent) {
			this._conversationId = conversationId;
			this._messageId = messageId;
			this._newContent = newContent;
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