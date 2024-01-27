package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;

public class UserInviteUseCase extends UseCase<UserInviteUseCase.InputValues, UserInviteUseCase.OutputValues> {

    private DataStorage _dataStorage;
    private Hasher _hasher;

    public UserInviteUseCase(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        // Check and invite user to group
        return new OutputValues(ResultCodes.SUCCESS, "User has been added to group");
    }

    public static class InputValues {
        private User _admin;
        private User _user;
        private String _groupID;

        public InputValues(User admin, User user, String groupId) {
            _admin = admin;
            _user = user;
            _groupID = groupId;
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