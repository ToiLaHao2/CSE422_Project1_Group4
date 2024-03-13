package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class LeaveGroupUseCase extends UseCase<LeaveGroupUseCase.InputValues, LeaveGroupUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public LeaveGroupUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		User user = _dataStorage.getUsers().getById(input._userID);
		String groupID = input.getGroupID();

		PublicGroup publicGroup = _dataStorage.getPublicGroup().getById(groupID);
		if (publicGroup != null) {
			publicGroup.removeMember(user);
			return new OutputValues(ResultCodes.SUCCESS);
		}

		PrivateGroup privateGroup = _dataStorage.getPrivateGroup().getById(groupID);
		if (privateGroup != null) {
			privateGroup.removeMember(user);
			return new OutputValues(ResultCodes.SUCCESS);
		}

		return new OutputValues(ResultCodes.FAILED);
	}

	public static class InputValues {
		private String _userID;
		private String _groupID;

		public InputValues(String userID, String groupID) {
			this._userID = userID;
			this._groupID = groupID;
		}

		public String getUserID() {
			return _userID;
		}

		public String getGroupID() {
			return _groupID;
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