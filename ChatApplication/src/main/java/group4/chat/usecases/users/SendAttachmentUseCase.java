package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;

public class SendAttachmentUseCase
        extends UseCase<SendAttachmentUseCase.InputValues, SendAttachmentUseCase.OutputValues> {

    private DataStorage _dataStorage;
    private Hasher _hasher;

    public SendAttachmentUseCase(DataStorage dataStorage, Hasher hasher) {
        _dataStorage = dataStorage;
        _hasher = hasher;
    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        // code to activate leave group
        return new OutputValues(ResultCodes.SUCCESS, "Sending attachment successfull");
    }

    public static class InputValues {
        private User _sender;
        private String _receiverID;
        private Byte[] _attachments;

        public InputValues(User sender, String receiverID, Byte[] attachments) {
            _sender = sender;
            _receiverID = receiverID;
            _attachments = attachments;
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