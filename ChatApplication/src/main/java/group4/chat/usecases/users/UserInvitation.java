package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;

public class UserInvitation extends UseCase<UserRegistration.InputValues, UserRegistration.OutputValues> {

    private DataStorage _dataStorage;
    private Hasher _hasher;

    public UserInvitation(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    public OutputValues execute(InputValues input) throws Exception {
        return new OutputValues(ResultCodes.SUCCESS, "");
    }

    public static class InputValues {
        private User _admin;
        private User _user;
        private String _groupID;

        public InputValues(User admin, User user, String groupId) {
            _admin = admin;
            _user = user;
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

    @Override
    public group4.chat.usecases.users.UserRegistration.OutputValues execute(
            group4.chat.usecases.users.UserRegistration.InputValues input) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}