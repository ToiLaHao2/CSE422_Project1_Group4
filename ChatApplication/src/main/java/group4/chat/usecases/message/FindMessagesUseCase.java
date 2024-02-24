package group4.chat.usecases.message;

import java.util.ArrayList;
import java.util.List;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;

public class FindMessagesUseCase 
    extends UseCase<FindMessagesUseCase.InputValues, FindMessagesUseCase.OutputValues> {

    public FindMessagesUseCase() {

    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
        List<Message> foundMessages = new ArrayList<>();
        List<Conversation> conversations = dataStorage.getAllConversations();
        if (conversations != null && !conversations.isEmpty()) {
            for (Conversation conversation : conversations) {
                for (Message message : conversation.get_messages()) {
                    if (message.get_content().contains(input.getKeyword())) {
                        foundMessages.add(message);
                    }
                }
            }
            return new OutputValues(ResultCodes.SUCCESS, foundMessages);
        } else {
            return new OutputValues(ResultCodes.FAILED, "No conversations found");
        }
        }
    

   

    public static class InputValues {
        private final String keyword;

        public InputValues(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }

    public static class OutputValues {
        private int _resultCode;
        private List<Message> foundMessages;
        private String _message;

        public OutputValues(int resultCode, List<Message> foundMessages) {
            this._resultCode = resultCode;
            this.foundMessages = foundMessages;
        }
        public OutputValues(int resultCode, String message) {
            _message = message;
            _resultCode = resultCode;
        }

        public int getResultCode() {
            return _resultCode;
        }
        

        public String get_message() {
            return _message;
        }
        public List<Message> getFoundMessages() {
            return foundMessages;
        }
    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }

}