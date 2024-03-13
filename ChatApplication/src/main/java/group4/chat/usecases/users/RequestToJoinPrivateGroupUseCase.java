package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.GroupRequest;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RequestToJoinPrivateGroupUseCase
		extends UseCase<RequestToJoinPrivateGroupUseCase.InputValues, RequestToJoinPrivateGroupUseCase.OutputValues> {

	private DataStorage _dataStorage;

	public RequestToJoinPrivateGroupUseCase(DataStorage _dataStorage) {
		super();
		this._dataStorage = _dataStorage;
	}

	public static class InputValues {
		private String _requestingUserID;
		private String _groupID;

		public InputValues(String requestingUserID, String groupID) {
			this._requestingUserID = requestingUserID;
			this._groupID = groupID;
		}

		public String getRequestingUserId() {
			return _requestingUserID;
		}

		public String getGroupId() {
			return _groupID;
		}
	}

	public static class OutputValues {
		private boolean _requestApproved;
		private int _resultCode;

		public OutputValues(boolean requestApproved, int resultCode) {
			this._requestApproved = requestApproved;
			this._resultCode = resultCode;
		}

		public boolean isRequestApproved() {
			return _requestApproved;
		}

		public static class ResultCodes {
			public static final int SUCCESS = 1;
			public static final int FAILED = 0;
		}
	}

	public OutputValues execute(InputValues input) {
		PrivateGroup group = _dataStorage.getPrivateGroup().getById(input.getGroupId());
		User requestingUser = _dataStorage.getUsers().getById(input.getRequestingUserId());

		if (group.getGroupUsers().contains(requestingUser)) {
			return new OutputValues(false, ResultCodes.FAILED);
		}

		for (GroupRequest request : group.getGroupRequests()) {
			if (request.getRequestingUser().equals(requestingUser)) {
				return new OutputValues(false, ResultCodes.FAILED);
			}
		}
		
		group.requestToJoin(requestingUser);

		return new OutputValues(true, ResultCodes.SUCCESS);
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}
}
