package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.domains.User.UserBuilder;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;

public class UserRegistration
        extends UseCase<UserRegistration.InputValues, UserRegistration.OutputValues> {
    private DataStorage _dataStorage;
    private Hasher _hasher;

    public UserRegistration(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    @Override
    public OutputValues execute(InputValues input) {
        User user = new UserBuilder(input._username, _hasher.hash(input._password)).build();
        _dataStorage.getUsers().add(user);
        return new OutputValues(ResultCodes.SUCCESS, "You sign up sucessfully");
    }

    public static class InputValues {
        private String _username;
        private String _password;

        public InputValues(String username, String password) {
            _username = username;
            _password = password;
        }
    }

    public static class OutputValues {
        private final int _resultCode;
        private final String _message;

        public OutputValues(int resultCode, String message) {
            _message = message;
            _resultCode = resultCode;
        }

        public int getResultCode(){
            return _resultCode;
        }

        public String getMessage(){
            return _message;
        }
    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }
}