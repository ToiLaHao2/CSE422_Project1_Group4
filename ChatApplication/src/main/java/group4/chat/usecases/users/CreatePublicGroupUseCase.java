package group4.chat.usecases.users;

import java.util.ArrayList;
import java.util.Random;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.usecases.UseCase;
import group4.chat.usecases.adapters.DataStorage;

public class CreatePublicGroupUseCase
		extends UseCase<CreatePublicGroupUseCase.InputValues, CreatePublicGroupUseCase.OutputValues> {
	private DataStorage _dataStorage;

	public CreatePublicGroupUseCase(DataStorage dataStorage) {
		_dataStorage = dataStorage;
	}

	@Override
	public OutputValues execute(InputValues input) {
		ArrayList<String> userIDs = input.getUserIDs();
		String joinCode = generateJoinCode();
		PublicGroup publicGroup = new PublicGroup(joinCode);
		
		for (int i = 0; i < userIDs.size(); i++) {
			User member = _dataStorage.getUsers().getById(userIDs.get(i));
			publicGroup.addMember(member);
		}
		
		_dataStorage.getPublicGroup().add(publicGroup);
		return new OutputValues(ResultCodes.SUCCESS, "Public group created successfully with join code: " + joinCode);
	}

	private String generateJoinCode() {
		String _joinCode;
		Random rd = new Random(5);
		
		_joinCode = rd.toString();
		
		return _joinCode;
	}

	public static class InputValues {
		private ArrayList<String> _userIDs;

		public InputValues(ArrayList<String> userIDs) {
			this._userIDs = userIDs;
		}

		public ArrayList<String> getUserIDs() {
			return _userIDs;
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
