package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.Random;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;


public class CreatePublicGroupUseCase
        extends UseCase<CreatePublicGroupUseCase.InputValues, CreatePublicGroupUseCase.OutputValues> {
    private DataStorage _dataStorage;
    private User user;

    public CreatePublicGroupUseCase(DataStorage dataStorage) {
        _dataStorage = dataStorage;
    }

    @Override
    public OutputValues execute(InputValues input) {
        String joinCode = generateJoinCode();
        PublicGroup publicGroup = new PublicGroup(joinCode);
        _dataStorage.getPublicGroup().add(publicGroup);
        publicGroup.addMember(executeInput(input.users));
        return new OutputValues(ResultCodes.SUCCESS, "Public group created successfully with join code: " + joinCode);

    }

    private String generateJoinCode() {
        String _joinCode;
        Random rd = new Random(5);
        _joinCode = rd.toString();
        return _joinCode;
    }
    private User executeInput(ArrayList<User> users){
        for (User user:users)
        return user;
        return user;
    }

    public static class InputValues {
        private ArrayList<User> users;

        public InputValues(ArrayList<User> users) {
            this.users = users;
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
