package group4.chat.usecases.message;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;
import group4.chat.message.Conversation;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ViewConversationUseCase
		extends UseCase<ViewConversationUseCase.InputValues, ViewConversationUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public static class InputValues {
		private User user;

		public InputValues(User user) {
			this.user = user;
		}

		public User getUser() {
			return user;
		}
	}

	public static class OutputValues {
		private List<String> joinedConversations;
		private int resultCode;

		public OutputValues(List<String> joinedConversations, int resultCode) {
			this.joinedConversations = joinedConversations;
			this.resultCode = resultCode;
		}

		public List<String> getJoinedConversations() {
			return joinedConversations;
		}

		public int getResultCode() {
			return resultCode;
		}
	}

	public OutputValues execute(InputValues input) {
		User user = input.getUser();
		List<Conversation> userConversations = user.getConversations();

		if (userConversations != null && !userConversations.isEmpty()) {
			List<String> joinedConversations = convertConversationsToStrings(userConversations);
			return new OutputValues(joinedConversations, ResultCodes.SUCCESS);
		} else {
			return new OutputValues(null, ResultCodes.FAILED);
		}
	}

	private List<String> convertConversationsToStrings(List<Conversation> conversations) {
		List<String> conversationStrings = new ArrayList<>();
		
		for (Conversation conversation : conversations) {
			conversationStrings.add("ID: " + conversation.getConversationId() + ", Participants: "
					+ conversation.getParticipantsAsString());
		}
		return conversationStrings;
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}
}
