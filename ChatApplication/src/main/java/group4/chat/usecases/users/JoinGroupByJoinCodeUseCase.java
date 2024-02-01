package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.adapters.Hasher;

public class JoinGroupByJoinCodeUseCase extends UseCase<JoinGroupByJoinCodeUseCase.InputValues, JoinGroupByJoinCodeUseCase.OutputValues> {
    private DataStorage _dataStorage;
    private Hasher _hasher;

    public JoinGroupByJoinCodeUseCase(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        return new OutputValues(ResultCodes.SUCCESS, "User has been added to group");
    }

    public static class InputValues {
        private String _userID;
        private String _groupID;
        private String _joinCode;

        public InputValues(String userID, String _joinCode,String _groupID) {
            _userID = userID;
            this._joinCode=_joinCode;
            this._groupID=_groupID;
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
