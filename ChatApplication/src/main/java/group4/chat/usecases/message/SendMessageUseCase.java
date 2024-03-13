package group4.chat.usecases.message;

import group4.chat.usecases.adapters.DataStorage;

import java.io.FileOutputStream;
import java.util.UUID;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.message.Message;

public class SendMessageUseCase extends UseCase<SendMessageUseCase.InputValues, SendMessageUseCase.OutputValues> {

	private DataStorage _dataStorage;

	public SendMessageUseCase(DataStorage dataStorage) {
		this._dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		User sender = _dataStorage.getUsers().getById(input.getSenderID());
		User receiver = _dataStorage.getUsers().getById(input.getReceiverId());
		if (sender == null || receiver == null) {
			return new OutputValues(ResultCodes.FAILED, "Sender or receiver not found");
		}
		if (input._attachment != null) {
			String attachmentId = saveAttachment(input._attachment);
			sendMessageWithAttachment(input._messageId, input._senderID, input._receiverId, input._content,
					attachmentId);
			return new OutputValues(ResultCodes.SUCCESS, "Sending message successfull");
		} else {
			sendMessage(input._messageId, input._senderID, input._receiverId, input._content);
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
		private byte[] _attachment;
		private String _receiverId;
		private String _content;
		private int _messageId;
		private String _senderID;

		public InputValues(byte[] attachment, String receiverId, String content, int messageId, String senderID) {
			this._attachment = attachment;
			this._receiverId = receiverId;
			this._content = content;
			this._messageId = messageId;
			this._senderID = senderID;
		}

		public byte[] getAttachment() {
			return _attachment;
		}

		public String getReceiverId() {
			return _receiverId;
		}

		public String getContent() {
			return _content;
		}

		public int getMessageId() {
			return _messageId;
		}

		public String getSenderID() {
			return _senderID;
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