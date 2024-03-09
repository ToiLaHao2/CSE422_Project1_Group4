package group4.chat.usecases.message;


import java.util.Set;

import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RemoveMessageUseCase
		extends UseCase<RemoveMessageUseCase.InputValues, RemoveMessageUseCase.OutputValues> {
            private DataStorage dataStorage;

	public RemoveMessageUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
	
	}

	@Override
	public OutputValues execute(InputValues input) {
        String userId = input.getUserId();
        String conversationId = input.getConversationId();
        int messageId = input.getMessageId();

        Conversation conversation = dataStorage.getConversation(conversationId);
        if (conversation == null) {
            return new OutputValues(ResultCodes.FAILED, "Conversation not found");
        }
        Message message = conversation.getMessageById(messageId);
        if (message == null) {
            return new OutputValues(ResultCodes.FAILED, "Message not found in the conversation");
        }
        //Need 1 more condition
        conversation.deleteMessage(message);
        dataStorage.updateConversation(conversation);
        return new OutputValues(ResultCodes.SUCCESS, "Message deleted successfully");

        
	

	}

	public static class InputValues {
        private final String userId;
        private final String conversationId;
        private final int messageId;

        public InputValues(String userId, String conversationId, int messageId) {
            this.userId = userId;
            this.conversationId = conversationId;
            this.messageId = messageId;
        }

        public String getUserId() {
            return userId;
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
