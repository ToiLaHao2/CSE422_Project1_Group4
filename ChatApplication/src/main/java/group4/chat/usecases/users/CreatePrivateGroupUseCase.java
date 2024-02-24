package group4.chat.usecases.users;

import java.util.ArrayList;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class CreatePrivateGroupUseCase
		extends UseCase<CreatePrivateGroupUseCase.InputValues, CreatePrivateGroupUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public CreatePrivateGroupUseCase(DataStorage dataStorage) {
		_dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		ArrayList<String> userIDs = input.getUserIDs();
		User creator = _dataStorage.getUsers().getById(userIDs.get(0));
		PrivateGroup privateGroup = new PrivateGroup(creator, "GroupID");
		for (int i = 1; i < userIDs.size(); i++) {
			User member = _dataStorage.getUsers().getById(userIDs.get(i));
			privateGroup.addMember(member);
		}
		_dataStorage.getPrivateGroup().add(privateGroup);
		return new OutputValues(ResultCodes.SUCCESS, "Private group created successfully");
	}

	public static class InputValues {
		private ArrayList<String> userIDs;

		public InputValues(ArrayList<String> userIDs) {
			this.userIDs = userIDs;
		}

		public ArrayList<String> getUserIDs() {
			return userIDs;
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
