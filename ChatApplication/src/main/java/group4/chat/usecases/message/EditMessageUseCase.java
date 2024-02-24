package group4.chat.usecases.message;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;


public class EditMessageUseCase
        extends UseCase<EditMessageUseCase.InputValues, EditMessageUseCase.OutputValues> {
    private DataStorage dataStorage;

    public EditMessageUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        Conversation conversation = dataStorage.getConversation(input.conversationId);
        Message message = conversation.getMessageById(input.messageId);
        message.set_content(input.newContent);

        return new OutputValues(ResultCodes.SUCCESS, "Message edited successfully");
    }

    public static class InputValues {
        private final String conversationId;
        private final int messageId;
        private final String newContent;

        public InputValues(String conversationId, int messageId, String newContent) {
            this.conversationId = conversationId;
            this.messageId = messageId;
            this.newContent = newContent;
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

public class EditMessageUseCase extends UseCase<EditMessageUseCase.InputValues, EditMessageUseCase.OutputValues> {
	private DataStorage dataStorage;

	public EditMessageUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		Conversation conversation = dataStorage.getConversation(input.conversationId);
		Message message = conversation.getMessageById(input.messageId);
		message.set_content(input.newContent);

		return new OutputValues(ResultCodes.SUCCESS, "Message edited successfully");
	}

	public static class InputValues {
		private final String conversationId;
		private final int messageId;
		private final String newContent;

		public InputValues(String conversationId, int messageId, String newContent) {
			this.conversationId = conversationId;
			this.messageId = messageId;
			this.newContent = newContent;
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