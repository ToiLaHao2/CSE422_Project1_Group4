package group4.chat.usecases.message;

import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.message.Conversation;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ViewAllConversationsUseCase
		extends UseCase<ViewAllConversationsUseCase.InputValues, ViewAllConversationsUseCase.OutputValues> {
	private DataStorage dataStorage;

	public ViewAllConversationsUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		List<Conversation> userConversations = dataStorage.getAllConversations();

		StringBuilder messageBuilder = new StringBuilder("Conversations you have joined:\n");

		for (Conversation conversation : userConversations) {
			if (conversation.get_user1() != null && conversation.get_user2() != null) {
				String otherUser = conversation.get_user1().equals(input.getCurrentUser().getId())
						? conversation.get_user2()
						: conversation.get_user1();

				User otherUserObj = dataStorage.getUsers().getById(otherUser);

				if (otherUserObj != null) {
					messageBuilder.append("Conversation ID: ").append(conversation.getConversationId())
							.append(", with user: ").append(otherUserObj.get_fullName()).append("\n");
				}
			} else if (conversation.get_group() != null) {
				// Conversation within a group
				PrivateGroup privateGroup = dataStorage.getPrivateGroup().getById(conversation.get_group());

				if (privateGroup != null) {
					messageBuilder.append("Conversation ID: ").append(conversation.getConversationId())
							.append(", within group: ").append(privateGroup.getName()).append("\n");

				} else {
					PublicGroup publicGroup = dataStorage.getPublicGroup().getById(conversation.get_group());

					if (publicGroup != null) {
						messageBuilder.append("Conversation ID: ").append(conversation.getConversationId())
								.append(", within group: ").append(publicGroup.getName()).append("\n");
					}
				}
			}
		}

		return new OutputValues(ResultCodes.SUCCESS, messageBuilder.toString());

	}

	public static class InputValues {
		private User currentUser;

		public InputValues(User currentUser) {
			this.currentUser = currentUser;
		}

		public User getCurrentUser() {
			return currentUser;
		}

	}

	public static class OutputValues {
		private final int _resultCode;
		private final String _message;

		public OutputValues(int resultCode, String message) {
			_message = message;
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
		}

		public String getMessage() {
			return _message;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}
