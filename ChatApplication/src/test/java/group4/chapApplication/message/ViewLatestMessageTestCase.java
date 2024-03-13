package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.ViewLatestMessageUseCase;
import group4.chat.usecases.message.ViewLatestMessageUseCase.InputValues;

class ViewLatestMessageTestCase {
	@Test
	public void testViewLatestMessages() throws Exception {
		String conversationId = "1";
		LocalDateTime upToTime = LocalDateTime.now();

		InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();

		Message message = new Message(1, "sender1", "receiver1", "message1");
		Message message1 = new Message(2, "sender11", "receiver11", "message11");
		Message message2 = new Message(3, "sender111", "receiver111", "message111");
		Message message3 = new Message(4, "sender1111", "receiver1111", "message1111");

		Conversation conversation = new Conversation(conversationId);
		conversation.addMessage(message);
		conversation.addMessage(message1);
		conversation.addMessage(message2);
		conversation.addMessage(message3);

		dataStorage.addConversation(conversation);

		ViewLatestMessageUseCase useCase = new ViewLatestMessageUseCase();

		ViewLatestMessageUseCase.InputValues inputValues = new InputValues(conversationId, 3, upToTime);

		ViewLatestMessageUseCase.OutputValues outputValues = useCase.execute(inputValues);

		System.out.println("Expected Result: 1");
		System.out.println("Actual Result: " + outputValues.get_resultCode());

		assertEquals(ViewLatestMessageUseCase.ResultCodes.SUCCESS, outputValues.get_resultCode());
	}

}
