package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.usecases.message.ViewConversationUseCase;

class ViewConversationTestCase {
	 private ViewConversationUseCase _viewConversationUseCase;
	    private InMemoryDataStorage _dataStorage;

	    @BeforeEach
	    void setUp() {
	        _dataStorage = new InMemoryDataStorage();
	        _viewConversationUseCase = new ViewConversationUseCase();
	    }

	    @Test
	    public void testViewConversation_Success() {
	        User user = new User("user1", "123");

	        ViewConversationUseCase.InputValues inputValues = new ViewConversationUseCase.InputValues(user);
	        ViewConversationUseCase.OutputValues outputValues = _viewConversationUseCase.execute(inputValues);

	        assertEquals(ViewConversationUseCase.ResultCodes.FAILED, outputValues.getResultCode());
	    }

	    @Test
	    public void testViewConversation_NoConversation() {
	        User user = new User("user1", "123");
	        
	        Conversation conversation = new Conversation();
	        conversation.set_user1(user.getId());
	        conversation.set_user2("user2");

	        ViewConversationUseCase.InputValues inputValues = new ViewConversationUseCase.InputValues(user);
	        ViewConversationUseCase.OutputValues outputValues = _viewConversationUseCase.execute(inputValues);

	        assertEquals(ViewConversationUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
	    }
}
