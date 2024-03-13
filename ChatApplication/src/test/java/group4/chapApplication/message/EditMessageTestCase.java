package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.EditMessageUseCase;

class EditMessageTestCase {

	private EditMessageUseCase _editMessageUseCase;
	private InMemoryDataStorage _dataStorage;

	@BeforeEach
	void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_editMessageUseCase = new EditMessageUseCase(_dataStorage);
	}

	@Test
	void testEditMessageSuccess() throws Exception {
		String conversationId = "123";
		int messageId = 456;
		String newContent = "New message content";

		Conversation conversation = new Conversation(conversationId);
		Message message = new Message(messageId, "Old message content");
		conversation.addNewSendingMessage(message);
		
		_dataStorage.addConversation(conversation);

		EditMessageUseCase.InputValues inputValues = new EditMessageUseCase.InputValues(conversationId, messageId,
				newContent);

		EditMessageUseCase.OutputValues outputValues = _editMessageUseCase.execute(inputValues);

		assertEquals(EditMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals(newContent, message.get_content());
	}
}
