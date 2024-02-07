package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;

import java.util.List;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;
import group4.chat.file.File;

public class SendMessageUseCase extends UseCase<SendMessageUseCase.InputValues, SendMessageUseCase.OutputValues> {

    private DataStorage _dataStorage;
    private Hasher _hasher;

    public SendMessageUseCase(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        // code to activate leave group
        return new OutputValues(ResultCodes.SUCCESS, "Sending message successfull");
    }

    public static class InputValues {
        private String _senderID;
        private String _receiverID;
        private String _message;
        private List<File> _files;


        public InputValues(String senderID, String receiverID, String message, List<File> files) {
            _senderID = senderID;
            _receiverID = receiverID;
            _message = message;
            _files = files;
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