package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.message.RecordLastMessageUseCase;

class RecordLastMessageTestCase {
	private RecordLastMessageUseCase _recordMessage;
	private InMemoryDataStorage _dataStorage;

	@BeforeEach
	void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_recordMessage = new RecordLastMessageUseCase(_dataStorage);
	}

	@Test
	public void testRecordLastMessage_Success() throws Exception {
		String userId = "user1";
		String conversationId = "conver1";

		Conversation conversation = new Conversation();
		conversation.set_conversationId(conversationId);
		conversation.addNewSendingMessage(new Message(123, "Hello"));

		_dataStorage.addConversation(conversation);

		RecordLastMessageUseCase.InputValues inputValues = new RecordLastMessageUseCase.InputValues(userId,
				conversationId);
		RecordLastMessageUseCase.OutputValues outputValues = _recordMessage.execute(inputValues);

		assertEquals(RecordLastMessageUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
	}

	@Test
	public void testRecordLastMessage_ConversationNotFound() throws Exception {
		String userId = "user1";
		String conversationId = "nonExistentConver";

		RecordLastMessageUseCase.InputValues inputValues = new RecordLastMessageUseCase.InputValues(userId,
				conversationId);
		RecordLastMessageUseCase.OutputValues outputValues = _recordMessage.execute(inputValues);

		assertEquals(RecordLastMessageUseCase.ResultCodes.FAILED, outputValues.getResultCode());
	}

}
