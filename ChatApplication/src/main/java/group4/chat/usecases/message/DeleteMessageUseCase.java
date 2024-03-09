package group4.chat.usecases.message;

import java.io.File;
import java.util.ArrayList;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class DeleteMessageUseCase extends UseCase<DeleteMessageUseCase.InputValues, DeleteMessageUseCase.OutputValues> {
    // private DataStorage dataStorage;

    public DeleteMessageUseCase() {
        // this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
        Conversation conversation = dataStorage.getConversation(input.conversationId);
        if (conversation != null) {
            Message message = conversation.getMessageById(input.messageId);
            if (message != null) {
                if (message.get_attachments() != null && !message.get_attachments().isEmpty()) {
                    deleteAttachments(message.get_attachments());
                }
                conversation.deleteMessage(message);
                return new OutputValues(ResultCodes.SUCCESS, "Message deleted successfully");
            } else {
                return new OutputValues(ResultCodes.FAILED, "Message not found");
            }
        } else {
            return new OutputValues(ResultCodes.FAILED, "Conversation not found");
        }
    }

    private void deleteAttachments(ArrayList<String> attachments) {
        for (String attachmentId : attachments) {
            File file = new File(attachmentId);
            if (file.exists()) {
                file.delete();
            }
        }

    }

    public static class InputValues {
        private String conversationId;
        private int messageId;

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