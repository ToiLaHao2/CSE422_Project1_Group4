package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.message.EditMessageUseCase;

class EditMessageTestCase {

	private EditMessageUseCase editMessageUseCase;
	private DataStorage dataStorage;

	@BeforeEach
	void setUp() {
		dataStorage = new InMemoryDataStorage();
		editMessageUseCase = new EditMessageUseCase(dataStorage);
	}

	@Test
	void testEditMessageSuccess() throws Exception {
		String conversationId = "123";
		int messageId = 456;
		String newContent = "New message content";

		Conversation conversation = new Conversation(conversationId);
		Message message = new Message(messageId, "Old message content");
		conversation.addNewSendingMessage(message);
		dataStorage.getConversation(conversation.getConversationId());

		EditMessageUseCase.InputValues inputValues = new EditMessageUseCase.InputValues(conversationId, messageId,
				newContent);

		EditMessageUseCase.OutputValues outputValues = editMessageUseCase.execute(inputValues);

		assertEquals(EditMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("Message edited successfully", outputValues.getMessage());
		assertEquals(newContent, message.get_content());
	}
}
