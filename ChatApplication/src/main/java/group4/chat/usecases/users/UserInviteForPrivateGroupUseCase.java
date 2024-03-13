package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;

public class UserInviteForPrivateGroupUseCase

        extends UseCase<UserInviteForPrivateGroupUseCase.InputValues, UserInviteForPrivateGroupUseCase.OutputValues> {
    private DataStorage _dataStorage;

    public UserInviteForPrivateGroupUseCase(DataStorage dataStorage) {
        this._dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        String adminID = input.getAdminID();
        String userId = input.getUserId();
        String groupId = input.getGroupId();
        
        User admin = _dataStorage.getUsers().getById(adminID);
        User user = _dataStorage.getUsers().getById(userId);
        PrivateGroup privateGroup = _dataStorage.getPrivateGroup().getById(groupId);

        if (admin == null || privateGroup == null || user == null) {
            return new OutputValues(ResultCodes.FAILED, "User or group not found");
        }
        if (privateGroup.findAdmin(admin) == false) {
            return new OutputValues(ResultCodes.FAILED, "User is not an admin of the group");
        }
        
        String inviteMessage = "You have been invited to join the private group: " + privateGroup.getId();
        user.receiveGroupInvite(inviteMessage);
        privateGroup.addMember(user);
        
        return new OutputValues(ResultCodes.SUCCESS, "User has been invited to join the group");
    }

    public static class InputValues {
        private String _adminID;
        private String _userId;
        private String _groupId;

        public InputValues(String adminID, String userId, String groupId) {
            this._adminID = adminID;
            this._userId = userId;
            this._groupId = groupId;
        }

        public String getAdminID() {
            return _adminID;
        }

        public String getUserId() {
            return _userId;
        }

        public String getGroupId() {
            return _groupId;
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