package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;

public class UserInviteForPublicGroupUseCase extends UseCase<UserInviteForPublicGroupUseCase.InputValues, UserInviteForPublicGroupUseCase.OutputValues> {

    private DataStorage _dataStorage;
    private Hasher _hasher;

    public UserInviteForPublicGroupUseCase(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        User user = _dataStorage.getUsers().getById(input._userID);
        return new OutputValues(ResultCodes.SUCCESS, "User has been added to group");
    }

    public static class InputValues {
        private String _userID;
        private String _groupID;

        public InputValues(String userID, String groupId) {
            _userID = userID;
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