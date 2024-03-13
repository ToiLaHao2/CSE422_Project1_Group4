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
	private LeaveGroupUseCase _leaveGroupUseCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_leaveGroupUseCase = new LeaveGroupUseCase(_dataStorage);
	}

	@Test
	public void testLeavePublicGroupSuccess() throws Exception {
		String joinCode = "group123";
		String userName = "John";
		String groupId = "Mai1234";
		String userId = "Mai123";

		User user = new User(userName, "123");
		user.setId(userId);
		
		PublicGroup publicGroup = new PublicGroup(joinCode);
		publicGroup.setId(groupId);
		publicGroup.addMember(user);
		
		_dataStorage.getUsers().add(user);
		_dataStorage.getPublicGroup().add(publicGroup);

		LeaveGroupUseCase.InputValues inputValues = new LeaveGroupUseCase.InputValues(userId, groupId);

		LeaveGroupUseCase.OutputValues outputValues = _leaveGroupUseCase.execute(inputValues);

		assertEquals(LeaveGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User has left the group", outputValues.getMessage());
		assertFalse(publicGroup.getGroupUsers().contains(user), "User should be removed from the group");

	}

	@Test
	public void testLeavePrivateGroupSuccess() throws Exception {
		String userName = "John";
		String groupId = "Mai123";
		String userId = "Mai123";

		User user = new User(userName, "123");
		user.setId(userId);
		
		PrivateGroup privateGroup = new PrivateGroup(user, null);
		privateGroup.setId(groupId);
		
		_dataStorage.getUsers().add(user);
		_dataStorage.getPrivateGroup().add(privateGroup);

		LeaveGroupUseCase.InputValues inputValues = new LeaveGroupUseCase.InputValues(userId, groupId);

		LeaveGroupUseCase.OutputValues outputValues = _leaveGroupUseCase.execute(inputValues);

		assertEquals(LeaveGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User has left the group", outputValues.getMessage());
		assertFalse(privateGroup.getGroupUsers().contains(user), "User should be removed from the group");
	}

	@Test
	public void testLeaveGroupGroupNotFound() throws Exception {
		String userId = "user1";
		String nonExistentGroupId = "nonExistentGroup";

		User user = new User(userId, "123");
		_dataStorage.getUsers().add(user);

		LeaveGroupUseCase.InputValues inputValues = new LeaveGroupUseCase.InputValues(userId, nonExistentGroupId);

		LeaveGroupUseCase.OutputValues outputValues = _leaveGroupUseCase.execute(inputValues);

		assertEquals(LeaveGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Group not found", outputValues.getMessage());
	}

}
