package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.GroupRequest;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RequestToJoinPrivateGroup
		extends UseCase<RequestToJoinPrivateGroup.InputValues, RequestToJoinPrivateGroup.OutputValues> {
	private DataStorage _dataStorage;	
	
	public static class InputValues {
        private User requestingUser;
        private PrivateGroup group;

        public InputValues(User requestingUser, PrivateGroup group) {
            this.requestingUser = requestingUser;
            this.group = group;
        }

        public User getRequestingUser() {
            return requestingUser;
        }

        public PrivateGroup getGroup() {
            return group;
        }
    }

    public static class OutputValues {
        private boolean requestApproved;
        private int _resultCode; 

        public OutputValues(boolean requestApproved) {
            this.requestApproved = requestApproved;
        }

        public boolean isRequestApproved() {
            return requestApproved;
        }
    }

	public OutputValues execute(InputValues input) {
		PrivateGroup group = input.getGroup();
		User requestingUser = input.getRequestingUser();

		if (group.getGroupUsers().contains(requestingUser)) {
			return new OutputValues(false);
		}

		for (GroupRequest request : group.getGroupRequests()) {
			if (request.getRequestingUser().equals(requestingUser)) {
				return new OutputValues(false);
			}
		}

		GroupRequest request = new GroupRequest(requestingUser, group);
		group.requestToJoin(requestingUser);

		return new OutputValues(true);
	}
	
	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}
}
