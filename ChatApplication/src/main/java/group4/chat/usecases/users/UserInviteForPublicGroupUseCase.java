package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class UserInviteForPublicGroupUseCase
		extends UseCase<UserInviteForPublicGroupUseCase.InputValues, UserInviteForPublicGroupUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public UserInviteForPublicGroupUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		String groupId = input.getGroupId();
		
		User user = _dataStorage.getUsers().getById(input.getUserID());
		PublicGroup publicGroup = _dataStorage.getPublicGroup().getById(groupId);

		if (publicGroup != null) {
			if (publicGroup.getGroupUsers().contains(user)) {
				return new OutputValues(ResultCodes.FAILED);
			} else {
				publicGroup.addMember(user);
				return new OutputValues(ResultCodes.SUCCESS);
			}
		} else {
			return new OutputValues(ResultCodes.FAILED);
		}
	}

	public static class InputValues {
		private String _groupId;
		private String _userID;

		public InputValues(String groupId, String userID) {
			this._groupId = groupId;
			this._userID = userID;
		}

		public String getGroupId() {
			return _groupId;
		}

		public String getUserID() {
			return _userID;
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