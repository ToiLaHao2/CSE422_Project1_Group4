package group4.chapApplication.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.CreatePublicGroupUseCase;

class CreatePublicGroupTestCase {

	private DataStorage _dataStorage;
	private CreatePublicGroupUseCase _useCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_useCase = new CreatePublicGroupUseCase(_dataStorage);
	}

	@Test
	public void testCreatePublicGroup_Success() {
		String userId1 = "user1";
		String userId2 = "user2";
		String userId3 = "user3";

		ArrayList<String> userIDs = new ArrayList<>();

		userIDs.add(userId1);
		userIDs.add(userId2);
		userIDs.add(userId3);

		User user1 = new User(userId1, "123");
		User user2 = new User(userId2, "123");
		User user3 = new User(userId3, "123");

		_dataStorage.getUsers().add(user1);
		_dataStorage.getUsers().add(user2);
		_dataStorage.getUsers().add(user3);

		CreatePublicGroupUseCase.InputValues inputValues = new CreatePublicGroupUseCase.InputValues(userIDs);
		CreatePublicGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(CreatePublicGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertTrue(outputValues.getMessage().startsWith("Public group created successfully with join code:"));
	}
}
