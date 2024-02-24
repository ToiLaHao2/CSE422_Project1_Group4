package group4.chat.usecases.users;

import java.util.ArrayList;

import group4.chat.domains.User;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class FindUsernameUseCase extends UseCase<FindUsernameUseCase.InputValues, FindUsernameUseCase.OutputValues> {
	private DataStorage dataStorage;

	public FindUsernameUseCase(DataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		String searchString = input.getSearchString();
		ArrayList<User> foundUsers = new ArrayList<>();
		for (User user : dataStorage.getUsers().getAll()) {
			if (user.get_fullName().toLowerCase().contains(searchString.toLowerCase())) {
				foundUsers.add(user);
			}
		}
		if (!foundUsers.isEmpty()) {
			return new OutputValues(ResultCodes.SUCCESS, "Users found", foundUsers);
		} else {
			return new OutputValues(ResultCodes.FAILED, "No users found");
		}

	}

	public static class InputValues {
		private String searchString;

		public InputValues(String searchString) {
			this.searchString = searchString;
		}

		public String getSearchString() {
			return searchString;
		}

	}

	public static class OutputValues {
		private final int resultCode;
		private final String message;
		private ArrayList<User> foundUsers;

		public OutputValues(int resultCode, String message, ArrayList<User> foundUsers) {
			this.resultCode = resultCode;
			this.message = message;
			this.foundUsers = foundUsers;
		}

		public OutputValues(int resultCode, String message) {
			this.resultCode = resultCode;
			this.message = message;
		}

		public int getResultCode() {
			return resultCode;
		}

		public String getMessage() {
			return message;
		}

		public ArrayList<User> getFoundUsers() {
			return foundUsers;
		}
	}

	public static class ResultCodes {
		public static final int SUCCESS = 1;
		public static final int FAILED = 0;
	}
}
