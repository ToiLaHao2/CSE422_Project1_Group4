package group4.chat.usecases.message;

import java.io.File;
import java.util.ArrayList;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;
import group4.chat.usecases.UseCase;

public class DeleteMessageUseCase extends UseCase<DeleteMessageUseCase.InputValues, DeleteMessageUseCase.OutputValues> {

	public DeleteMessageUseCase() {

	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
		Conversation conversation = dataStorage.getConversation(input.conversationId);
		if (conversation != null) {
			Message message = conversation.getMessageById(input.messageId);
			if (message != null) {
				if (message.get_attachments() != null && !message.get_attachments().isEmpty()) {
					deleteAttachments(message.get_attachments());
				}
				conversation.deleteMessage(message);
				return new OutputValues(ResultCodes.SUCCESS);
			} else {
				return new OutputValues(ResultCodes.FAILED);
			}
		} else {
			return new OutputValues(ResultCodes.FAILED);
		}
	}

	private void deleteAttachments(ArrayList<String> attachments) {
		for (String attachmentId : attachments) {
			File file = new File(attachmentId);
			if (file.exists()) {
				file.delete();
			}
		}

	}

	public static class InputValues {
		private String conversationId;
		private int messageId;

		public InputValues(String conversationId, int messageId) {
			this.conversationId = conversationId;
			this.messageId = messageId;
		}

		public String getConversationId() {
			return conversationId;
		}

		public int getMessageId() {
			return messageId;
		}
	}

	public static class OutputValues {
		private final int _resultCode;

		public OutputValues(int resultCode) {
			_resultCode = resultCode;
		}

		public int getResultCode() {
			return _resultCode;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}