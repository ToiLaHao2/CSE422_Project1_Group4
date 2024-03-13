package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RemoveUserFromAGroupUseCase
		extends UseCase<RemoveUserFromAGroupUseCase.InputValues, RemoveUserFromAGroupUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public RemoveUserFromAGroupUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		PrivateGroup group = _dataStorage.getPrivateGroup().getById(input.getGroupID());

		if (group == null) {
			return new OutputValues(ResultCodes.FAILED);
		}

		User userToRemove = _dataStorage.getUsers().getById(input.getUserID());

		if (userToRemove == null) {
			return new OutputValues(ResultCodes.FAILED);
		}

		User admin = _dataStorage.getUsers().getById(input.getAdminID());

		if (admin == null) {
			return new OutputValues(ResultCodes.FAILED);
		}

		if (!group.isAdmin(admin)) {
			return new OutputValues(ResultCodes.FAILED);
		}

		group.removeMember(userToRemove);
		
		return new OutputValues(ResultCodes.SUCCESS);

	}

	public static class InputValues {
		private final String _groupID;
		private final String _userID;
		private final String _adminID;

		public InputValues(String groupID, String userID, String adminID) {
			this._groupID = groupID;
			this._userID = userID;
			this._adminID = adminID;
		}

		public String getGroupID() {
			return _groupID;
		}

		public String getUserID() {
			return _userID;
		}

		public String getAdminID() {
			return _adminID;
		}

	}

	public static class OutputValues {
		private final int _resultCode;

		public OutputValues(int resultCode) {
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}