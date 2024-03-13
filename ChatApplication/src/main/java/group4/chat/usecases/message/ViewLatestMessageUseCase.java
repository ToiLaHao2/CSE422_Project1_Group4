package group4.chat.usecases.message;

import group4.chat.usecases.UseCase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.message.Conversation;
import group4.chat.message.Message;

public class ViewLatestMessageUseCase
		extends UseCase<ViewLatestMessageUseCase.InputValues, ViewLatestMessageUseCase.OutputValues> {

	public ViewLatestMessageUseCase() {

	}

	public static class InputValues {
		private int _numberOfLatestMessage;
		private LocalDateTime _upToTime;
		private String _conversationID;

		public InputValues(String conversationId, int _numberOfLatestMessage, LocalDateTime _upToTime) {
			super();
			this._conversationID = conversationId;
			this._numberOfLatestMessage = _numberOfLatestMessage;
			this._upToTime = _upToTime;
		}

		public int get_numberOfLatestMessage() {
			return _numberOfLatestMessage;
		}

		public void set_numberOfLatestMessage(int _numberOfLatestMessage) {
			this._numberOfLatestMessage = _numberOfLatestMessage;
		}

		public LocalDateTime get_upToTime() {
			return _upToTime;
		}

		public void set_upToTime(LocalDateTime _upToTime) {
			this._upToTime = _upToTime;
		}

		public String getConversationId() {
			return this._conversationID;
		}

	}

	public static class OutputValues {
		private List<String> _messages;
		private int _resultCode;

		public OutputValues(List<String> messages, int _resultCode) {
			super();
			this._messages = messages;
			this._resultCode = _resultCode;
		}

		public List<String> getMessages() {
			return _messages;
		}

		public int get_resultCode() {
			return _resultCode;
		}

	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		InMemoryDataStorage dataStorage = InMemoryDataStorage.getInstance();
		List<String> messages = new ArrayList<>();
		Conversation conversation = dataStorage.getConversation(input.getConversationId());

		if (conversation != null) {
			List<Message> latestMessages = conversation.getLatestMessage(input.get_numberOfLatestMessage(),
					input.get_upToTime());

			for (Message message : latestMessages) {
				messages.add(message.get_content());
			}
			return new OutputValues(messages, ResultCodes.SUCCESS);
		} else {
			return new OutputValues(messages, ResultCodes.FAILED);
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}

}
