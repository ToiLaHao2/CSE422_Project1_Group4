package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ListGroupsContainUserUseCase
		extends UseCase<ListGroupsContainUserUseCase.InputValues, ListGroupsContainUserUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public ListGroupsContainUserUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		User user = _dataStorage.getUsers().getById(input._userID);

		List<PublicGroup> publicGroups = _dataStorage.getPublicGroup().getAll();
		List<PrivateGroup> privateGroups = _dataStorage.getPrivateGroup().getAll();

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
		private String _userID;

		public InputValues(String userID) {
			this._userID = userID;
		}

		public String getUserID() {
			return _userID;
		}
	}

	public static class OutputValues {
		private final int _resultCode;
		private List<PublicGroup> _userPublicGroups;
		private List<PrivateGroup> _userPrivateGroups;

		public OutputValues(int resultCode, List<PublicGroup> userPublicGroups, List<PrivateGroup> userPrivateGroups) {
			this._resultCode = resultCode;
			this._userPublicGroups = userPublicGroups;
			this._userPrivateGroups = userPrivateGroups;
		}

		public int getResultCode() {
			return _resultCode;
		}

		public List<PublicGroup> getUserPublicGroups() {
			return _userPublicGroups;
		}

		public List<PrivateGroup> getUserPrivateGroups() {
			return _userPrivateGroups;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}