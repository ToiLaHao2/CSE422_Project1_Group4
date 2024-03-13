package group4.chapApplication.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.CreatePrivateGroupUseCase;

class CreatePrivateGroupTestCase {

	private DataStorage _dataStorage;
	private CreatePrivateGroupUseCase _createPrivateGroupUseCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_createPrivateGroupUseCase = new CreatePrivateGroupUseCase(_dataStorage);
	}

	@Test
	public void testCreatePrivateGroup_Success() {
		String creatorId = "user1";
		String memberId1 = "user2";
		String memberId2 = "user3";
		
		ArrayList<String> userIDs = new ArrayList<>();
		
		userIDs.add(creatorId);
		userIDs.add(memberId1);
		userIDs.add(memberId2);

		User creator = new User(creatorId, "123");
		User member1 = new User(memberId1, "123");
		User member2 = new User(memberId2, "123");

		_dataStorage.getUsers().add(creator);
		_dataStorage.getUsers().add(member1);
		_dataStorage.getUsers().add(member2);																								

		CreatePrivateGroupUseCase.InputValues inputValues = new CreatePrivateGroupUseCase.InputValues(userIDs);

		CreatePrivateGroupUseCase.OutputValues outputValues = _createPrivateGroupUseCase.execute(inputValues);

		assertEquals(CreatePrivateGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("Private group created successfully", outputValues.getMessage());
	}

}
