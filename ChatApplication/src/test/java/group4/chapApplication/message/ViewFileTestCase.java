package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.ViewFilesUseCase;

class ViewFileTestCase {

	private ViewFilesUseCase _viewFilesUseCase;

    @BeforeEach
    void setUp() {
        _viewFilesUseCase = new ViewFilesUseCase();
    }

    @Test
    void testViewFilesSuccess() throws Exception {
        String groupId = "123";
        InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();

        Conversation conversation = new Conversation(groupId);
        Message message1 = new Message(1, "Hello");
        message1.set_attachment("file1.txt");
        Message message2 = new Message(2, "World");
        message2.set_attachment("file2.txt");
        conversation.addMessage(message1);
        conversation.addMessage(message2);
        dataStorage.addConversation(conversation);
        dataStorage.getConversation(conversation.getConversationId());

        ViewFilesUseCase.InputValues inputValues = new ViewFilesUseCase.InputValues(groupId);
        ViewFilesUseCase.OutputValues outputValues = _viewFilesUseCase.execute(inputValues);

        assertEquals(ViewFilesUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
    }

    @Test
    void testViewFilesConversationNotFound() throws Exception {
        String groupId = "nonexistent";
        ViewFilesUseCase.InputValues inputValues = new ViewFilesUseCase.InputValues(groupId);

        ViewFilesUseCase.OutputValues outputValues = _viewFilesUseCase.execute(inputValues);

        assertEquals(ViewFilesUseCase.ResultCodes.FAILED, outputValues.getResultCode());
        assertEquals("Conversation not found", outputValues.get_message());
    }

}
