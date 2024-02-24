package group4.chat.usecases.message;

import group4.chat.usecases.adapters.DataStorage;

import java.io.FileOutputStream;
import java.util.UUID;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.message.Message;

public class SendMessageUseCase extends UseCase<SendMessageUseCase.InputValues, SendMessageUseCase.OutputValues> {
	private DataStorage dataStorage;

	public SendMessageUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		User sender = dataStorage.getUsers().getById(input.getSenderID());
		User receiver = dataStorage.getUsers().getById(input.getReceiverId());

		if (sender == null || receiver == null) {
			return new OutputValues(ResultCodes.FAILED, "Sender or receiver not found");
		}

		if (input.attachment != null) {
			String attachmentId = saveAttachment(input.attachment);
			sendMessageWithAttachment(input.messageId, input.senderID, input.receiverId, input.content, attachmentId);
			return new OutputValues(ResultCodes.SUCCESS, "Sending message successfull");
		} else {
			sendMessage(input.messageId, input.senderID, input.receiverId, input.content);
		}
		return new OutputValues(ResultCodes.SUCCESS, "Sending message successful");
	}

	private String saveAttachment(byte[] attachment) throws Exception {
		String attachmentId = UUID.randomUUID().toString();
		String filePath = "/path/to/attachments/" + attachmentId + ".dat";

		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			fos.write(attachment);
		}
		return attachmentId;
	}

	private void sendMessage(int messageId, String senderId, String receiverId, String content) {
		new Message(messageId, senderId, receiverId, content);
	}

	private void sendMessageWithAttachment(int messageId, String senderId, String receiverId, String content,
			String attachmentId) {
		new Message(messageId, senderId, receiverId, content, attachmentId);
	}

	public static class InputValues {
		private byte[] attachment;
		private String receiverId;
		private String content;
		private int messageId;
		private String senderID;

		public InputValues(byte[] attachment, String receiverId, String content, int messageId, String senderID) {
			this.attachment = attachment;
			this.receiverId = receiverId;
			this.content = content;
			this.messageId = messageId;
			this.senderID = senderID;
		}

		public byte[] getAttachment() {
			return attachment;
		}

		public String getReceiverId() {
			return receiverId;
		}

		public String getContent() {
			return content;
		}

		public int getMessageId() {
			return messageId;
		}

		public String getSenderID() {
			return senderID;
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