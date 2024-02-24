package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.UseCase;

public class ListGroupsContainUserUseCase
		extends UseCase<ListGroupsContainUserUseCase.InputValues, ListGroupsContainUserUseCase.OutputValues> {
	private InMemoryDataStorage dataStorage;

	public ListGroupsContainUserUseCase(InMemoryDataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		User user = dataStorage.getUsers().getById(input.userID);

		List<PublicGroup> publicGroups = dataStorage.getPublicGroup().getAll();
		List<PrivateGroup> privateGroups = dataStorage.getPrivateGroup().getAll();

		List<PublicGroup> userPublicGroups = new ArrayList<>();
		List<PrivateGroup> userPrivateGroups = new ArrayList<>();
		
		for (PublicGroup group : publicGroups) {
			if (group.getGroupUsers().contains(user)) {
				userPublicGroups.add(group);
			}
		}
		
		for (PrivateGroup group : privateGroups) {
			if (group.getGroupUsers().contains(user)) {
				userPrivateGroups.add(group);
			}
		}

		return new OutputValues(ResultCodes.SUCCESS, userPublicGroups, userPrivateGroups);

	}

	public static class InputValues {
		private String userID;

		public InputValues(String userID) {
			this.userID = userID;
		}

		public String getUserID() {
			return userID;
		}

	}

	public static class OutputValues {
		private final int resultCode;
		private List<PublicGroup> userPublicGroups;
		private List<PrivateGroup> userPrivateGroups;

		public OutputValues(int resultCode, List<PublicGroup> userPublicGroups, List<PrivateGroup> userPrivateGroups) {
			this.resultCode = resultCode;
			this.userPublicGroups = userPublicGroups;
			this.userPrivateGroups = userPrivateGroups;
		}

		public int getResultCode() {
			return resultCode;
		}

		public List<PublicGroup> getUserPublicGroups() {
			return userPublicGroups;
		}

		public List<PrivateGroup> getUserPrivateGroups() {
			return userPrivateGroups;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}