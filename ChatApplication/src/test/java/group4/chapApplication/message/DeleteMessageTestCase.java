package group4.chapApplication.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.DeleteMessageUseCase;

class DeleteMessageTestCase {

	private DeleteMessageUseCase _useCase;
	private InMemoryDataStorage _dataStorage;

	@BeforeEach
	public void setUp() {
		_useCase = new DeleteMessageUseCase();
		_dataStorage = InMemoryDataStorage.getInstance();
	}

	@Test
	public void testDeleteMessageSuccess() throws Exception {
		String conversationId = "testConversation";
		int messageId = 1;
		ArrayList<String> attachments = new ArrayList<>();

		Conversation conversation = new Conversation("user1", "user2", conversationId);
		Message message = new Message(messageId, "Sender", "Receiver", "Content");
		conversation.addMessage("Sender", "Receiver", "Content", attachments);

		DeleteMessageUseCase.InputValues inputValues = new DeleteMessageUseCase.InputValues(conversationId, messageId);

		DeleteMessageUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(DeleteMessageUseCase.ResultCodes.SUCCESS, outputValues.getMessage());
		assertEquals("Message deleted successfully", outputValues.getMessage());
		assertEquals(conversation.getMessageById(messageId), "Conversation should not have any messages");
	}

	@Test
	public void testDeleteMessage_ConversationNotFound() throws Exception {
		String conversationId = "nonExistentConversation";
		int messageId = 1;

		DeleteMessageUseCase.InputValues inputValues = new DeleteMessageUseCase.InputValues(conversationId, messageId);

		DeleteMessageUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(DeleteMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Conversation not found", outputValues.getMessage());
	}
}
