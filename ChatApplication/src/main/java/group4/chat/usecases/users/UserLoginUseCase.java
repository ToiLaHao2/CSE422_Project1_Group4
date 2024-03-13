package group4.chat.usecases.users;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.adapters.Hasher;

public class UserLoginUseCase extends UseCase<UserLoginUseCase.InputValues, UserLoginUseCase.OutputValues> {
	private DataStorage _dataStorage;
	private Hasher _hasher;

	public UserLoginUseCase(DataStorage dataStorage, Hasher hasher) {
		_dataStorage = dataStorage;
		_hasher = hasher;
	}

	@Override
	public OutputValues execute(InputValues input) {
		String username = input._username;
		String password = input._password;

		User user = _dataStorage.getUsers().getFirst(u -> u.get_firstName().equals(username));

		if (user != null) {
			try {
				String hashedPassword = _hasher.hash(password);
				
				if (hashedPassword.equals(user.get_hashedPassword())) {
					return new OutputValues(ResultCodes.SUCCESS, "You logged in successfully");
				} else {
					return new OutputValues(ResultCodes.FAILED, "Invalid username or password");
				}
			} catch (Exception exception) {
				return new OutputValues(ResultCodes.FAILED, "Error occurred");
			}
		} else {
			return new OutputValues(ResultCodes.FAILED, "User not found");
		}
	}

	public static class InputValues {
		private String _username;
		private String _password;

		public InputValues(String username, String password) {
			_username = username;
			_password = password;
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
