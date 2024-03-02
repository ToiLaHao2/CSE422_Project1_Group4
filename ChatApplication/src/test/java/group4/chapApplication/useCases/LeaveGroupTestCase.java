package group4.chapApplication.useCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.LeaveGroupUseCase;

class LeaveGroupTestCase {
	private DataStorage _dataStorage;
	private LeaveGroupUseCase _useCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_useCase = new LeaveGroupUseCase(_dataStorage);
	}

	@Test
	public void testLeavePublicGroupSuccess() throws Exception {
		String userId = "user1";
		String publicGroupId = "publicGroup1";

		User user = new User(userId, "123");
		PublicGroup publicGroup = new PublicGroup(publicGroupId);

		publicGroup.addMember(user);
		_dataStorage.getPublicGroup().add(publicGroup);
		_dataStorage.getUsers().add(user);

		LeaveGroupUseCase.InputValues inputValues = new LeaveGroupUseCase.InputValues(userId, publicGroupId);

		LeaveGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(LeaveGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User has left the group", outputValues.getMessage());

		assertFalse(publicGroup.getGroupUsers().contains(user), "User should be removed from the public group");
	}

	@Test
	public void testLeavePrivateGroupSuccess() throws Exception {
		String userId = "user1";
		String privateGroupId = "privateGroup1";

		User user = new User(userId, "123");
		PrivateGroup privateGroup = new PrivateGroup(user, privateGroupId);

		privateGroup.addMember(user);
		_dataStorage.getPrivateGroup().add(privateGroup);
		_dataStorage.getUsers().add(user);

		LeaveGroupUseCase.InputValues inputValues = new LeaveGroupUseCase.InputValues(userId, privateGroupId);

		LeaveGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(LeaveGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User has left the group", outputValues.getMessage());

		assertFalse(privateGroup.getGroupUsers().contains(user), "User should be removed from the private group");
	}

	@Test
	public void testLeaveGroupGroupNotFound() throws Exception {
		String userId = "user1";
		String nonExistentGroupId = "nonExistentGroup";

		User user = new User(userId, "123");
		_dataStorage.getUsers().add(user);

		LeaveGroupUseCase.InputValues inputValues = new LeaveGroupUseCase.InputValues(userId, nonExistentGroupId);

		LeaveGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(LeaveGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Group not found", outputValues.getMessage());
	}

}
