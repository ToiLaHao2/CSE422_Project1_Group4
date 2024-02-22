package group4.chapApplication.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.users.ListGroupsContainUserUseCase;

class ListGroupsContainUserTestCase {

	private InMemoryDataStorage _dataStorage;
	private ListGroupsContainUserUseCase _useCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_useCase = new ListGroupsContainUserUseCase(_dataStorage);
	}

	@Test
	public void testListGroupsContainUserSuccess() throws Exception {
		String userId = "user1";
		User user = new User(userId, "123");

		PublicGroup publicGroup1 = new PublicGroup("publicGroup1");
		PublicGroup publicGroup2 = new PublicGroup("publicGroup2");

		publicGroup1.addMember(user);
		publicGroup2.addMember(user);

		_dataStorage.getPublicGroup().add(publicGroup1);
		_dataStorage.getPublicGroup().add(publicGroup2);
		_dataStorage.getUsers().add(user);

		PrivateGroup privateGroup1 = new PrivateGroup(user, "privateGroup1");
		PrivateGroup privateGroup2 = new PrivateGroup(user, "privateGroup2");

		privateGroup1.addMember(user);
		privateGroup2.addMember(user);

		_dataStorage.getPrivateGroup().add(privateGroup1);
		_dataStorage.getPrivateGroup().add(privateGroup2);

		ListGroupsContainUserUseCase.InputValues inputValues = new ListGroupsContainUserUseCase.InputValues(userId);

		ListGroupsContainUserUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(ListGroupsContainUserUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());

		List<PublicGroup> userPublicGroups = outputValues.getUserPublicGroups();
		List<PrivateGroup> userPrivateGroups = outputValues.getUserPrivateGroups();

		assertEquals(2, userPublicGroups.size(), "User should be a member of 2 public groups");
		assertTrue(userPublicGroups.contains(publicGroup1), "PublicGroup1 should be in the user's public groups");
		assertTrue(userPublicGroups.contains(publicGroup2), "PublicGroup2 should be in the user's public groups");

		assertEquals(2, userPrivateGroups.size(), "User should be a member of 2 private groups");
		assertTrue(userPrivateGroups.contains(privateGroup1), "PrivateGroup1 should be in the user's private groups");
		assertTrue(userPrivateGroups.contains(privateGroup2), "PrivateGroup2 should be in the user's private groups");
	}

	@Test
	public void testListGroupsContainUser_UserNotInAnyGroups() throws Exception {
		String userId = "user1";
		User user = new User(userId, "123");
		_dataStorage.getUsers().add(user);

		ListGroupsContainUserUseCase.InputValues inputValues = new ListGroupsContainUserUseCase.InputValues(userId);

		ListGroupsContainUserUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(ListGroupsContainUserUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());

		List<PublicGroup> userPublicGroups = outputValues.getUserPublicGroups();
		List<PrivateGroup> userPrivateGroups = outputValues.getUserPrivateGroups();

		assertTrue(userPublicGroups.isEmpty(), "User should not be a member of any public group");
		assertTrue(userPrivateGroups.isEmpty(), "User should not be a member of any private group");
	}

	@Test
	public void testListGroupsContainUser_UserNotInSomeGroups() throws Exception {
		String userId = "user1";
		User user = new User(userId, "123");

		PublicGroup publicGroup1 = new PublicGroup("publicGroup1");
		PublicGroup publicGroup2 = new PublicGroup("publicGroup2");

		publicGroup1.addMember(user);

		_dataStorage.getPublicGroup().add(publicGroup1);
		_dataStorage.getPublicGroup().add(publicGroup2);
		_dataStorage.getUsers().add(user);

		PrivateGroup privateGroup1 = new PrivateGroup(user, "privateGroup1");
		PrivateGroup privateGroup2 = new PrivateGroup(user, "privateGroup2");

		privateGroup1.addMember(user);

		_dataStorage.getPrivateGroup().add(privateGroup1);
		_dataStorage.getPrivateGroup().add(privateGroup2);

		ListGroupsContainUserUseCase.InputValues inputValues = new ListGroupsContainUserUseCase.InputValues(userId);

		ListGroupsContainUserUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(ListGroupsContainUserUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());

		List<PublicGroup> userPublicGroups = outputValues.getUserPublicGroups();
		List<PrivateGroup> userPrivateGroups = outputValues.getUserPrivateGroups();

		assertEquals(1, userPublicGroups.size(), "User should be a member of 1 public group");
		assertTrue(userPublicGroups.contains(publicGroup1), "PublicGroup1 should be in the user's public groups");
		assertFalse(userPublicGroups.contains(publicGroup2), "PublicGroup2 should not be in the user's public groups");

		assertEquals(1, userPrivateGroups.size(), "User should be a member of 1 private group");
		assertTrue(userPrivateGroups.contains(privateGroup1), "PrivateGroup1 should be in the user's private groups");
		assertFalse(userPrivateGroups.contains(privateGroup2),
				"PrivateGroup2 should not be in the user's private groups");
	}

}
