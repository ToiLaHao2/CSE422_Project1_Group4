package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.GroupRequest;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
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
        private User _requestingUser;
        private PrivateGroup _group;

        public InputValues(User requestingUser, PrivateGroup group) {
            this._requestingUser = requestingUser;
            this._group = group;
        }

        public User getRequestingUser() {
            return _requestingUser;
        }

        public PrivateGroup getGroup() {
            return _group;
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
    }

	public OutputValues execute(InputValues input) {
		PrivateGroup group = input.getGroup();
        User requestingUser = input.getRequestingUser();
        
		User user = _dataStorage.getUsers().getById(input._requestingUser.getId());
		PublicGroup publicGroup = _dataStorage.getPublicGroup().getById(input.getGroup().getId());

        if (group.getGroupUsers().contains(requestingUser)) {
            return new OutputValues(false, ResultCodes.FAILED);
        }

        for (GroupRequest request : group.getGroupRequests()) {
            if (request.getRequestingUser().equals(requestingUser)) {
                return new OutputValues(false, ResultCodes.FAILED);
            }
        }

        GroupRequest request = new GroupRequest(requestingUser, group);
        group.requestToJoin(requestingUser);

        return new OutputValues(true, ResultCodes.SUCCESS);
	}
	
	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}
}
