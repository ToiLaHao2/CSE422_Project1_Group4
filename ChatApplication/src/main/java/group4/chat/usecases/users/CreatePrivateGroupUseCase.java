package group4.chat.usecases.users;

import java.util.ArrayList;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class CreatePrivateGroupUseCase
        extends UseCase<CreatePrivateGroupUseCase.InputValues, CreatePrivateGroupUseCase.OutputValues> {
    private DataStorage _dataStorage;

    public CreatePrivateGroupUseCase(DataStorage dataStorage) {
        _dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) {
        ArrayList<User> users = input.getUsers();
        User creator = users.get(0);
        PrivateGroup privateGroup = new PrivateGroup(creator, "GroupID");
        _dataStorage.getPrivateGroup().add(privateGroup);

        return new OutputValues(ResultCodes.SUCCESS, "Private group created successfully");

    }

    public static class InputValues {
        private ArrayList<User> users;

        public InputValues(ArrayList<User> users) {
            this.users = users;
        }

        public ArrayList<User> getUsers() {
            return users;
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
