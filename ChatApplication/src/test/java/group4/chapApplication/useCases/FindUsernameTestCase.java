package group4.chapApplication.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.FindUsernameUseCase;

class FindUsernameTestCase {
	private DataStorage _dataStorage;
	private FindUsernameUseCase _useCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_useCase = new FindUsernameUseCase(_dataStorage);
	}

	@Test
	public void testFindUsernameSuccess() {
		String searchString = "John";
		User user1 = new User("John", "123");
		User user2 = new User("user2", "123");
		
		_dataStorage.getUsers().add(user1);
		_dataStorage.getUsers().add(user2);

		FindUsernameUseCase.InputValues inputValues = new FindUsernameUseCase.InputValues(searchString);

		FindUsernameUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(FindUsernameUseCase.ResultCodes.SUCCESS, outputValues.getResultCode(),outputValues.getMessage());
		assertEquals("Users found", outputValues.getMessage());
		assertTrue(outputValues.getFoundUsers().contains(user1));
	}

	@Test
	public void testFindUsernameNoUsersFound() {
		String searchString = "Nonexistent";

		FindUsernameUseCase.InputValues inputValues = new FindUsernameUseCase.InputValues(searchString);

		FindUsernameUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(FindUsernameUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("No users found", outputValues.getMessage());
	}

}
