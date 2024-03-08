package group4.chat.usecases.message;

import java.util.ArrayList;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class ViewWhoReadMessageUseCase
		extends UseCase<ViewWhoReadMessageUseCase.InputValues, ViewWhoReadMessageUseCase.OutputValues> {

	public ViewWhoReadMessageUseCase() {
	}

	@Override
	public OutputValues execute(InputValues input) {
		
		return new OutputValues(ResultCodes.SUCCESS, "Private group created successfully");
	}

	public static class InputValues {
		

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
