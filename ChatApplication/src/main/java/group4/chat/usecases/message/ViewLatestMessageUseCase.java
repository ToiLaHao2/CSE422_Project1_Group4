package group4.chat.usecases.message;

import java.util.List;

import group4.chat.domains.User;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.adapters.Hasher;

public class ViewLatestMessageUseCase extends UseCase<ViewLatestMessageUseCase.InputValues, ViewLatestMessageUseCase.OutputValues> {
    private DataStorage dataStorage;

	public ViewLatestMessageUseCase(DataStorage dataStorage) {
        this.dataStorage=dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		try {
            String userId = input.getUserId();
            String conversationId = input.getConversationId();
            int k = input.getK();
            long timestamp = input.getTimestamp();

            Conversation conversation = dataStorage.getConversation(conversationId);

            if (conversation == null) {
                return new OutputValues(ResultCodes.FAILED, "Conversation not found");
            }

            List<Message> latestMessages = conversation.getLatestMessages(k, timestamp);

            return new OutputValues(ResultCodes.SUCCESS, latestMessages);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new OutputValues(ResultCodes.FAILED, "Error retrieving latest messages");
        }
		}

	public static class InputValues {
        private final String userId;
        private final String conversationId;
        private final int k;
        private final long timestamp;

        public InputValues(String userId, String conversationId, int k, long timestamp) {
            this.userId = userId;
            this.conversationId = conversationId;
            this.k = k;
            this.timestamp = timestamp;
        }

        public String getUserId() {
            return userId;
        }

        public String getConversationId() {
            return conversationId;
        }

        public int getK() {
            return k;
        }

        public long getTimestamp() {
            return timestamp;
        }
	}

	public static class OutputValues {
		private final int resultCode;
        private final Object resultData;

        public OutputValues(int resultCode, Object resultData) {
            this.resultCode = resultCode;
            this.resultData = resultData;
        }

        public int getResultCode() {
            return resultCode;
        }

        public Object getResultData() {
            return resultData;
        }
    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }
}
