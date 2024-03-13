package group4.chat.usecases.users;

import java.util.ArrayList;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class FindUsernameUseCase
        extends UseCase<FindUsernameUseCase.InputValues, FindUsernameUseCase.OutputValues> {
    private DataStorage _dataStorage;

    public FindUsernameUseCase(DataStorage dataStorage) {
        this._dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) {
        String searchString = input.getSearchString();
        ArrayList<User> foundUsers = new ArrayList<>();
        
        for (User user : _dataStorage.getUsers().getAll()) {
            if (user.get_firstName().equals(searchString)) {
                foundUsers.add(user);
            }
        }
        if (!foundUsers.isEmpty()) {
            return new OutputValues(ResultCodes.SUCCESS, "Users found", foundUsers);
        } else {
            return new OutputValues(ResultCodes.FAILED, "No users found");
        }

    }

    public static class InputValues {
        private String _searchString;

        public InputValues(String searchString) {
            this._searchString = searchString;
        }

        public String getSearchString() {
            return _searchString;
        }

    }

    public static class OutputValues {
        private final int _resultCode;
        private final String _message;
        private ArrayList<User> _foundUsers;

        public OutputValues(int resultCode, String message, ArrayList<User> foundUsers) {
            this._resultCode = resultCode;
            this._message = message;
            this._foundUsers = foundUsers != null ? foundUsers : new ArrayList<>();
        }

        public OutputValues(int resultCode, String message) {
            this._resultCode = resultCode;
            this._message = message;
            this._foundUsers = new ArrayList<>();

        }

        public int getResultCode() {
            return _resultCode;
        }

        public String getMessage() {
            return _message;
        }

        public ArrayList<User> getFoundUsers() {
            return _foundUsers;
        }
    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }

}
