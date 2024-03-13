package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.ViewWhoReadMessageUseCase;

class ViewWhoReadMessageTestCase {
    private ViewWhoReadMessageUseCase _viewWhoReadMessage;
    private InMemoryDataStorage _dataStorage;

    @BeforeEach
    void setUp() {
        _dataStorage = new InMemoryDataStorage();
        _viewWhoReadMessage = new ViewWhoReadMessageUseCase(_dataStorage);
    }

    @Test
    public void testViewWhoReadMessage_Success() {
        String conversationId = "conver1";
        String user1 = "user1";
        String user2 = "user2";
        int messageId = 1;

        Message message = new Message(1, "Hi");

        Conversation conversation = new Conversation();
        conversation.set_conversationId(conversationId);
        conversation.addNewSendingMessage(message);
        conversation.setLastReadMessage(user1, message);
        conversation.setLastReadMessage(user2, message);
        _dataStorage.addConversation(conversation);

        ViewWhoReadMessageUseCase.InputValues inputValues = new ViewWhoReadMessageUseCase.InputValues(conversationId,
                messageId);
        ViewWhoReadMessageUseCase.OutputValues outputValues = _viewWhoReadMessage.execute(inputValues);

        assertEquals(ViewWhoReadMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
    }

    @Test
    public void testViewWhoReadMessage_ConversationNotFound() {
        String conversationId = "nonExistentConv";
        int messageId = 1;

        ViewWhoReadMessageUseCase.InputValues inputValues = new ViewWhoReadMessageUseCase.InputValues(conversationId,
                messageId);
        ViewWhoReadMessageUseCase.OutputValues outputValues = _viewWhoReadMessage.execute(inputValues);

        assertEquals(ViewWhoReadMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
    }

    @Test
    public void testViewWhoReadMessage_MessageNotReadByAnyone() {
        String conversationId = "conv123";
        int messageId = 1;

        Conversation conversation = new Conversation();
        conversation.set_conversationId(conversationId);

        ViewWhoReadMessageUseCase.InputValues inputValues = new ViewWhoReadMessageUseCase.InputValues(conversationId,
                messageId);
        ViewWhoReadMessageUseCase.OutputValues outputValues = _viewWhoReadMessage.execute(inputValues);

        assertEquals(ViewWhoReadMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
    }

}
