package group4.chat.usecases.message;

import java.util.ArrayList;
import java.util.List;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;

public class ViewFilesUseCase extends UseCase<ViewFilesUseCase.InputValues, ViewFilesUseCase.OutputValues> {

    public ViewFilesUseCase() {
        

    }

    @Override
    public OutputValues execute(InputValues input) throws Exception {
        InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
        List<String> files = new ArrayList<>();
        Conversation conversation = dataStorage.getConversation(input.getGroupId());
        if (conversation != null) {
            for (Message message : conversation.get_messages()) {
                for (String attachmentId : message.get_attachments()) {
                    files.add(attachmentId);
                }
            }
            return new OutputValues(ResultCodes.SUCCESS, files);
        } else {
            return new OutputValues(ResultCodes.FAILED, "Conversation not found");
        }
        }
    

   

    public static class InputValues {
        private String groupId;

        public InputValues(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupId() {
            return groupId;
        }
    }

    public static class OutputValues {
        private int _resultCode;
        private List<String> files;
        private String _message;


        public OutputValues(int resultCode, List<String> files) {
            this._resultCode = resultCode;
            this.files = files;
        }
        public OutputValues(int resultCode, String message) {
            _message = message;
            _resultCode = resultCode;
        }

        public int getResultCode() {
            return _resultCode;
        }

        public List<String> getFiles() {
            return files;
        }
        public String get_message() {
            return _message;
        }
    }

    public static class ResultCodes {
        public static final int SUCCESS = 1;
        public static final int FAILED = 0;
    }


	public ViewFilesUseCase() {

	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
		List<String> files = new ArrayList<>();
		Conversation conversation = dataStorage.getConversation(input.getGroupId());

		if (conversation != null) {
			for (Message message : conversation.get_messages()) {
				for (String attachmentId : message.get_attachments()) {
					files.add(attachmentId);
				}
			}
			return new OutputValues(ResultCodes.SUCCESS, files);
		} else {
			return new OutputValues(ResultCodes.FAILED, "Conversation not found");
		}
	}

	public static class InputValues {
		private String groupId;

		public InputValues(String groupId) {
			this.groupId = groupId;
		}

		public String getGroupId() {
			return groupId;
		}
	}

	public static class OutputValues {
		private int _resultCode;
		private List<String> files;
		private String _message;

		public OutputValues(int resultCode, List<String> files) {
			this._resultCode = resultCode;
			this.files = files;
		}

		public OutputValues(int resultCode, String message) {
			_message = message;
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
		}

		public List<String> getFiles() {
			return files;
		}

		public String get_message() {
			return _message;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}


}