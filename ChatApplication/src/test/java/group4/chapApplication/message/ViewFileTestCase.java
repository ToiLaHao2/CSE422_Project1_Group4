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

	private ViewFilesUseCase viewFilesUseCase;

    @BeforeEach
    void setUp() {
        viewFilesUseCase = new ViewFilesUseCase();
    }

    @Test
    void testViewFilesSuccess() throws Exception {
        String groupId = "123";
        InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
        ViewFilesUseCase.InputValues inputValues = new ViewFilesUseCase.InputValues(groupId);

        Conversation conversation = new Conversation(groupId);
        Message message1 = new Message(1, "Hello");
        message1.set_attachment("file1.txt");
        Message message2 = new Message(2, "World");
        message2.set_attachment("file2.txt");
        conversation.addMessage(message1);
        conversation.addMessage(message2);
        dataStorage.getConversation(conversation.getConversationId());

        ViewFilesUseCase.OutputValues outputValues = viewFilesUseCase.execute(inputValues);

        assertEquals(ViewFilesUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
        assertEquals(Arrays.asList("file1.txt", "file2.txt"), outputValues.getFiles());
    }

    @Test
    void testViewFilesConversationNotFound() throws Exception {
        String groupId = "nonexistent";
        ViewFilesUseCase.InputValues inputValues = new ViewFilesUseCase.InputValues(groupId);

        ViewFilesUseCase.OutputValues outputValues = viewFilesUseCase.execute(inputValues);

        assertEquals(ViewFilesUseCase.ResultCodes.FAILED, outputValues.getResultCode());
        assertEquals("Conversation not found", outputValues.get_message());
    }

}
