package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Respository;

public class UserInviteForPrivateGroupUseCase
        extends UseCase<UserInviteForPrivateGroupUseCase.InputValues, UserInviteForPrivateGroupUseCase.OutputValues> {
    private DataStorage dataStorage;

    public UserInviteForPrivateGroupUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        String adminID = input.getAdminID();
        String userId = input.getUserId();
        String groupId = input.getGroupId();
        Respository<User> userRepository = dataStorage.getUsers();
        Respository<PrivateGroup> privateGroupRepository = dataStorage.getPrivateGroup();
        User admin = userRepository.getById(adminID);
        User user = userRepository.getById(userId);
        PrivateGroup privateGroup = privateGroupRepository.getById(groupId);

        if (admin == null || privateGroup == null || user == null) {
            return new OutputValues(ResultCodes.FAILED, "User or group not found");
        }
        if (!privateGroup.getGroupAdmins().contains(admin)) {
            return new OutputValues(ResultCodes.FAILED, "User is not an admin of the group");
        }
        String inviteMessage = "You have been invited to join the private group: " + privateGroup.getGroupID();
        user.receiveGroupInvite(inviteMessage);
        return new OutputValues(ResultCodes.SUCCESS, "User has been invited to join the group");
    }

    public static class InputValues {
        private String adminID;
        private String userId;
        private String groupId;

        public InputValues(String adminID, String userId, String groupId) {
            this.adminID = adminID;
            this.userId = userId;
            this.groupId = groupId;
        }

        public String getAdminID() {
            return adminID;
        }

        public String getUserId() {
            return userId;
        }

        public String getGroupId() {
            return groupId;
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