package group4.chapApplication.message;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.DeleteMessageUseCase;

class DeleteMessageTestCase {

	private DeleteMessageUseCase _deleteMessageUsecase;

	@BeforeEach
	public void setUp() {
		_deleteMessageUsecase = new DeleteMessageUseCase();
	}

	@Test
	public void testDeleteMessageSuccess() throws Exception {
		String groupName = "g3";
		String conversationId = "existingConversation";
		
		Conversation conversation = new Conversation(null, null, groupName, conversationId);
		Message message = new Message(1, "sender", "receiver", "Sample message");
		conversation.addNewSendingMessage(message);

		InMemoryDataStorage.getInstance().addConversation(conversation);
		int messageId = 1;
		DeleteMessageUseCase.InputValues inputValues = new DeleteMessageUseCase.InputValues(conversationId, messageId);

		DeleteMessageUseCase.OutputValues outputValues = _deleteMessageUsecase.execute(inputValues);

		assertEquals(DeleteMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("Message deleted successfully", outputValues.getMessage());

		assertNull(conversation.getMessageById(messageId));
	}

	@Test
	public void testDeleteMessage_ConversationNotFound() throws Exception {
		String conversationId = "nonExistentConversation";
		int messageId = 1;	

		DeleteMessageUseCase.InputValues inputValues = new DeleteMessageUseCase.InputValues(conversationId, messageId);

		DeleteMessageUseCase.OutputValues outputValues = _deleteMessageUsecase.execute(inputValues);

		assertEquals(DeleteMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Conversation not found", outputValues.getMessage());
	}
}
