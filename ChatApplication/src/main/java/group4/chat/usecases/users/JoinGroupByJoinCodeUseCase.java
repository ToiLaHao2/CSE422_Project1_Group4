package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.UseCase;

public class JoinGroupByJoinCodeUseCase
        extends UseCase<JoinGroupByJoinCodeUseCase.InputValues, JoinGroupByJoinCodeUseCase.OutputValues> {
    private InMemoryDataStorage dataStorage;

    public JoinGroupByJoinCodeUseCase(InMemoryDataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        String joinCode = input.getJoinCode();
        String userId = input.getUserID();
        String publicGroupId = input.getPublicGroupID();
       
        User user = dataStorage.getUsers().getById(userId);
        PublicGroup publicGroup = dataStorage.getPublicGroup().getById(publicGroupId);
        if (user != null && publicGroup != null && publicGroup.getJoinCode().equals(joinCode)) {
            if (!publicGroup.getGroupUsers().contains(user)) {
                publicGroup.addMember(user);
                return new OutputValues(ResultCodes.SUCCESS, "User has been added to the group");
            } else {
                return new OutputValues(ResultCodes.FAILED, "User is already a member of the group");
            }
        } else {
            return new OutputValues(ResultCodes.FAILED, "Invalid join code. Unable to join the group");
        }
    }

    public static class InputValues {
        private String joinCode;
        private String userID;
        private String publicGroupID;

        public InputValues(String joinCode, String userID, String publicGroupID) {
            this.joinCode = joinCode;
            this.userID = userID;
            this.publicGroupID = publicGroupID;
        }

        public String getJoinCode() {
            return joinCode;
        }

        public String getUserID() {
            return userID;
        }

        public String getPublicGroupID() {
            return publicGroupID;
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
