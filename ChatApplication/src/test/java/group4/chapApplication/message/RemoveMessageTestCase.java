package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.RemoveMessageUseCase;

class RemoveMessageTestCase {
    private RemoveMessageUseCase _removeMessage;
    private InMemoryDataStorage _dataStorage;

    @BeforeEach
    void setUp() {
        _dataStorage = new InMemoryDataStorage();
        _removeMessage = new RemoveMessageUseCase(_dataStorage);
    }

    @Test
    public void testRemoveMessage_Success() {
        String userId = "user1";
        String conversationId = "conver1";
        int messageId = 1;

        Conversation conversation = new Conversation();
        conversation.set_conversationId(conversationId);

        Message message = new Message(123, "Hello");
        message.set_messageId(messageId);

        conversation.addNewSendingMessage(message);
        _dataStorage.addConversation(conversation);

        _dataStorage.getConversation(conversationId);

        RemoveMessageUseCase.InputValues inputValues = new RemoveMessageUseCase.InputValues(userId, conversationId,
                messageId);
        RemoveMessageUseCase.OutputValues outputValues = _removeMessage.execute(inputValues);

        assertEquals(RemoveMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
    }

    @Test
    public void testRemoveMessage_ConversationNotFound() {
        String userId = "user1";
        String conversationId = "nonExistentConver1";
        int messageId = 1;

        RemoveMessageUseCase.InputValues inputValues = new RemoveMessageUseCase.InputValues(userId, conversationId,
                messageId);
        RemoveMessageUseCase.OutputValues outputValues = _removeMessage.execute(inputValues);

        assertEquals(RemoveMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
    }

    @Test
    public void testRemoveMessage_MessageNotFound() {
        String userId = "user123";
        String conversationId = "conv456";
        int messageId = 1;
        Conversation conversation = new Conversation();

        RemoveMessageUseCase.InputValues inputValues = new RemoveMessageUseCase.InputValues(userId, conversationId,
                messageId);
        RemoveMessageUseCase.OutputValues outputValues = _removeMessage.execute(inputValues);

        assertEquals(RemoveMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
    }

}
