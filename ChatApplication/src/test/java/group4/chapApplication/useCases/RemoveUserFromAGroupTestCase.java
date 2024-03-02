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
	private DataStorage dataStorage;
	private RemoveUserFromAGroupUseCase useCase;

	@BeforeEach
	public void setUp() {
		dataStorage = new InMemoryDataStorage();
		useCase = new RemoveUserFromAGroupUseCase(dataStorage);
	}

	@Test
	public void testRemoveUserFromGroupSuccess() {
		// Arrange
		String adminId = "admin1";
		String userIdToRemove = "userToRemove";
		String groupId = "privateGroup1";

		User admin = new User(adminId, "123");
		User userToRemove = new User(userIdToRemove, "123");

		PrivateGroup privateGroup = new PrivateGroup(admin, groupId);
		privateGroup.addAdmin(admin);
		privateGroup.addMember(userToRemove);

		dataStorage.getUsers().add(admin);
		dataStorage.getUsers().add(userToRemove);
		dataStorage.getPrivateGroup().add(privateGroup);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(groupId,
				userIdToRemove, adminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User removed from the group successfully", outputValues.getMessage());

		assertFalse(privateGroup.getGroupUsers().contains(userToRemove),
				"User should be removed from the private group");
	}

	@Test
	public void testRemoveUserFromGroupAdminNotAdminOfGroup() {
		String nonAdminId = "nonAdmin";
		String userIdToRemove = "userToRemove";
		String groupId = "privateGroup1";

		User nonAdmin = new User(nonAdminId, "123");
		User userToRemove = new User(userIdToRemove, "123");

		PrivateGroup privateGroup = new PrivateGroup(nonAdmin, groupId);
		privateGroup.addAdmin(nonAdmin);
		privateGroup.addMember(userToRemove);

		dataStorage.getUsers().add(nonAdmin);
		dataStorage.getUsers().add(userToRemove);
		dataStorage.getPrivateGroup().add(privateGroup);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(groupId,
				userIdToRemove, nonAdminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

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

		dataStorage.getUsers().add(admin);
		dataStorage.getUsers().add(userToRemove);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(
				nonExistentGroupId, userIdToRemove, adminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Group not found", outputValues.getMessage());
	}

	@Test
	public void testRemoveUserFromGroup_UserNotFound() {
		String adminId = "admin1";
		String nonExistentUserId = "nonExistentUser";
		String groupId = "privateGroup1";

		User admin = new User(adminId, "123");

		PrivateGroup privateGroup = new PrivateGroup(admin, groupId);
		privateGroup.addAdmin(admin);

		dataStorage.getUsers().add(admin);

		RemoveUserFromAGroupUseCase.InputValues inputValues = new RemoveUserFromAGroupUseCase.InputValues(groupId,
				nonExistentUserId, adminId);

		RemoveUserFromAGroupUseCase.OutputValues outputValues = useCase.execute(inputValues);

		assertEquals(RemoveUserFromAGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("User not found", outputValues.getMessage());
	}
}
