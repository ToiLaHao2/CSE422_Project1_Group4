package group4.chapApplication.useCases;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.RemoveUserFromAGroupUseCase;

class RemoveUserFromAGroupTestCase {
	private DataStorage _dataStorage;
	private RemoveUserFromAGroupUseCase _removeUserFromAGroupUseCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_removeUserFromAGroupUseCase = new RemoveUserFromAGroupUseCase(_dataStorage);
	}

	@Test
	public void testRemoveUserFromGroupSuccess() {
		String adminId = "admin1";
		String adminName = "admin";
		String userIdToRemove = "userToRemove1";
		String userNameToRemove = "userToRemove";
		String groupId = "privateGroup1";

		User admin = new User(adminName, "123");
		admin.setId(adminId);
		User userToRemove = new User(userNameToRemove, "123");
		userToRemove.setId(userIdToRemove);

		PrivateGroup privateGroup = new PrivateGroup(admin,null);
		privateGroup.setId(groupId);
		privateGroup.addAdmin(admin);
		privateGroup.addMember(userToRemove);

		_dataStorage.getUsers().add(admin);
		_dataStorage.getUsers().add(userToRemove);
		_dataStorage.getPrivateGroup().add(privateGroup);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(groupId,
				userIdToRemove, adminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = _removeUserFromAGroupUseCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User removed from the group successfully", outputValues.getMessage());

		assertFalse(privateGroup.getGroupUsers().contains(userToRemove),
				"User should be removed from the private group");
	}

	@Test
	public void testRemoveUserFromGroupAdminNotAdminOfGroup() {
		String nonAdminId = "nonAdmin1";
		String nonAdminName = "nonAdmin";
		String adminId = "admin1";
		String adminName = "admin";
		String userIdToRemove = "userToRemove1";
		String userNameToRemove = "userToRemove";
		String groupId = "privateGroup1";

		User admin = new User(adminName, "123");
		admin.setId(adminId);
		User userToRemove = new User(userNameToRemove, "123");
		userToRemove.setId(userIdToRemove);
		User nonAdmin = new User(nonAdminName, "123");
		nonAdmin.setId(nonAdminId);

		PrivateGroup privateGroup = new PrivateGroup(admin,null);
		privateGroup.setId(groupId);
		privateGroup.addAdmin(admin);
		privateGroup.addMember(userToRemove);

		_dataStorage.getUsers().add(nonAdmin);
		_dataStorage.getUsers().add(admin);
		_dataStorage.getUsers().add(userToRemove);
		_dataStorage.getPrivateGroup().add(privateGroup);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(groupId,
				userIdToRemove, nonAdminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = _removeUserFromAGroupUseCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("You are not an administrator of this group", outputValues.getMessage());

		assertTrue(privateGroup.getGroupUsers().contains(userToRemove),
				"User should not be removed from the private group");
	}

	@Test
	public void testRemoveUserFromGroupGroupNotFound() {
		String adminId = "admin1";
		String userIdToRemove = "userToRemove";
		String nonExistentGroupId = "nonExistentGroup";

		User admin = new User(adminId, "123");
		User userToRemove = new User(userIdToRemove, "123");

		_dataStorage.getUsers().add(admin);
		_dataStorage.getUsers().add(userToRemove);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(
				nonExistentGroupId, userIdToRemove, adminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = _removeUserFromAGroupUseCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Group not found", outputValues.getMessage());
	}

	@Test
	public void testRemoveUserFromGroup_UserNotFound() {
		String nonExistentUserId = "nonExistentUser";
		String adminId = "admin1";
		String adminName = "admin";
		String groupId = "privateGroup1";

		User admin = new User(adminName, "123");
		admin.setId(adminId);

		PrivateGroup privateGroup = new PrivateGroup(admin,null);
		privateGroup.setId(groupId);

		_dataStorage.getUsers().add(admin);
		_dataStorage.getPrivateGroup().add(privateGroup);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(groupId,
				nonExistentUserId, adminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = _removeUserFromAGroupUseCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("User not found", outputValues.getMessage());
	}
}
