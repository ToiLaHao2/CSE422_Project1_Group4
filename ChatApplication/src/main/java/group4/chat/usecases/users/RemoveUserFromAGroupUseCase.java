package group4.chat.usecases.users;



import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class RemoveUserFromAGroupUseCase
        extends UseCase<RemoveUserFromAGroupUseCase.InputValues, RemoveUserFromAGroupUseCase.OutputValues> {
    private DataStorage dataStorage;

    public RemoveUserFromAGroupUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) {
        PrivateGroup group = dataStorage.getPrivateGroup().getById(input.getGroupID());
        if (group == null) {
            return new OutputValues(ResultCodes.FAILED, "Group not found");
        }
        User userToRemove = dataStorage.getUsers().getById(input.getUserID());
        if (userToRemove == null) {
            return new OutputValues(ResultCodes.FAILED, "User not found");
        }
        User admin = dataStorage.getUsers().getById(input.getAdminID());
        if (admin == null) {
            return new OutputValues(ResultCodes.FAILED, "User not found");
        }
        if (!group.isAdmin(admin)) {
            return new OutputValues(ResultCodes.FAILED, "You are not an administrator of this group");
        }
        group.removeMember(userToRemove);
        return new OutputValues(ResultCodes.SUCCESS, "User removed from the group successfully");

    }

    public static class InputValues {
        private final String groupID;
        private final String userID;
        private final String adminID;

        public InputValues(String groupID, String userID, String adminID) {
            this.groupID = groupID;
            this.userID = userID;
            this.adminID = adminID;
        }

        public String getGroupID() {
            return groupID;
        }

        public String getUserID() {
            return userID;
        }

        public String getAdminID() {
            return adminID;
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