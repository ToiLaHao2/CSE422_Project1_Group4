package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.usecases.message.ViewAllConversationsUseCase;

class ViewAllConversationTestCase {
	private ViewAllConversationsUseCase _viewAllConversations;
	private InMemoryDataStorage _dataStorage;

	@BeforeEach
	void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_viewAllConversations = new ViewAllConversationsUseCase(_dataStorage);
	}

	@Test
	public void testViewAllConversations_Success() {
		User currentUser = new User("user1", "123");
		List<Conversation> userConversations = new ArrayList<>();

		Conversation privateConversation = new Conversation();
		privateConversation.set_user1(currentUser.getId());
		privateConversation.set_user2("user2");
		userConversations.add(privateConversation);

		Conversation groupConversation = new Conversation();
		groupConversation.set_group("group1");
		userConversations.add(groupConversation);
		
		_dataStorage.addConversation(privateConversation);
		_dataStorage.addConversation(groupConversation);

		ViewAllConversationsUseCase.InputValues inputValues = new ViewAllConversationsUseCase.InputValues(currentUser);
		ViewAllConversationsUseCase.OutputValues outputValues = _viewAllConversations.execute(inputValues);

		assertEquals(ViewAllConversationsUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
	}

	@Test
	public void testViewAllConversations_NoConversations() {
		User currentUser = new User("user1", "123");
		List<Conversation> userConversations = new ArrayList<>();

		ViewAllConversationsUseCase.InputValues inputValues = new ViewAllConversationsUseCase.InputValues(currentUser);
		ViewAllConversationsUseCase.OutputValues outputValues = _viewAllConversations.execute(inputValues);

		assertEquals(ViewAllConversationsUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
	}

}
