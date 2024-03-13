package group4.chat.usecases.users;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.domains.User;
import group4.chat.domains.User.UserBuilder;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.Hasher;
import group4.chat.usecases.adapters.Respository;

public class UserRegistrationUseCase
		extends UseCase<UserRegistrationUseCase.InputValues, UserRegistrationUseCase.OutputValues> {

	private DataStorage _dataStorage;
	private Hasher _hasher;

	public UserRegistrationUseCase(DataStorage dataStorage, Hasher hasher) {
		_dataStorage = dataStorage;
		_hasher = hasher;
	}

	@Override
	public OutputValues execute(InputValues input) throws Exception {
		boolean check = true;
		Respository<User> userRepository = _dataStorage.getUsers();
		
		for (User u : userRepository.getAll()) {
			if (u.get_firstName().equals(input._username))
				return new OutputValues(ResultCodes.FAILED);
		}
		
		int passwordStrength = input._password.length();
		
		if (passwordStrength <= 8) {
			return new OutputValues(ResultCodes.FAILED);
		}
		if (check == true) {
			User user = new UserBuilder(input._username, _hasher.hash(input._password)).build();
			_dataStorage.getUsers().add(user);
			
			return new OutputValues(ResultCodes.SUCCESS);
		}
		
		return new OutputValues(ResultCodes.FAILED);
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