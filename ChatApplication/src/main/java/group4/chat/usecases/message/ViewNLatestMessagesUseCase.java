package group4.chat.usecases.message;

import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

import java.util.List;

public class ViewNLatestMessagesUseCase
        extends UseCase<ViewNLatestMessagesUseCase.InputValues, ViewNLatestMessagesUseCase.OutputValues> {

    private DataStorage dataStorage;

    public ViewNLatestMessagesUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        Conversation conversation = dataStorage.getConversation(input.getConversationId());
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
        private String conversationId;
        private int n;

        public InputValues(String conversationId, int n) {
            this.conversationId = conversationId;
            this.n = n;
        }

        public String getConversationId() {
            return conversationId;
        }

        public int getN() {
            return n;
        }
    }

    public static class OutputValues {
        private int _resultCode;
        private List<Message> messages;
        private String _message;

        public OutputValues(int resultCode, List<Message> messages) {
            this._resultCode = resultCode;
            this.messages = messages;
        }
        public OutputValues(int resultCode, String message) {
			_message = message;
			_resultCode = resultCode;
		}

        public int getResultCode() {
            return _resultCode;
        }

        public List<Message> getMessages() {
            return messages;
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
