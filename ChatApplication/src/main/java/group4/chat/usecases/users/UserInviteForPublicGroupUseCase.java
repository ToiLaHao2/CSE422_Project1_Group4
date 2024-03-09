package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class UserInviteForPublicGroupUseCase

        extends UseCase<UserInviteForPublicGroupUseCase.InputValues, UserInviteForPublicGroupUseCase.OutputValues> {
    private DataStorage dataStorage;

    public UserInviteForPublicGroupUseCase(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {

        String groupId = input.getGroupId();
        User user = dataStorage.getUsers().getById(input.getUserID());

        PublicGroup publicGroup = dataStorage.getPublicGroup().getById(groupId);

        if (publicGroup != null) {
            if (!publicGroup.getGroupUsers().contains(user)) {
                publicGroup.addMember(user);
                return new OutputValues(ResultCodes.SUCCESS, "User has been added to the group");
            } else {
                return new OutputValues(ResultCodes.FAILED, "User is already a member of the group");
            }
        } else {
            return new OutputValues(ResultCodes.FAILED, "Invalid group ID. Unable to add user to the group");
        }
    }

    public static class InputValues {
        private String groupId;
        private String userID;

        public InputValues(String groupId, String userID) {
            this.groupId = groupId;
            this.userID = userID;
        }

        public String getGroupId() {
            return groupId;
        }

        public String getUserID() {
            return userID;
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