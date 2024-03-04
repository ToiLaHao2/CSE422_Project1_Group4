package group4.chapApplication.message;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.users.UserInviteForPublicGroupUseCase;

class InvitePublicGroupTestCase {
	private InMemoryDataStorage _dataStorage;
	private UserInviteForPublicGroupUseCase _useCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_useCase = new UserInviteForPublicGroupUseCase(_dataStorage);
	}

	@Test
	public void testUserInviteForPublicGroup_Success() throws Exception {
		String groupId = "group123";
		String user = "John";

		PublicGroup publicGroup = new PublicGroup(groupId);
    	_dataStorage.getPublicGroup().add(publicGroup);

		UserInviteForPublicGroupUseCase.InputValues inputValues = new UserInviteForPublicGroupUseCase.InputValues(groupId, user);
		UserInviteForPublicGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);
	

		assertEquals(UserInviteForPublicGroupUseCase.ResultCodes.SUCCESS, outputValues.getMessage());
		assertEquals("User has been added to the group", outputValues.getMessage());
		assertTrue(publicGroup.getGroupUsers().stream().anyMatch(u -> u.get_fullName().equals(user)));

	}

	@Test
	public void testUserInviteForPublicGroup_UserAlreadyMember() throws Exception {
		String groupId = "group123";
		String user = "John";

		PublicGroup publicGroup = new PublicGroup(groupId);
		User john = new User("John", "Doe", "password");
		publicGroup.addMember(john);
		_dataStorage.getPublicGroup().add(publicGroup);

		UserInviteForPublicGroupUseCase.InputValues inputValues = new UserInviteForPublicGroupUseCase.InputValues(
				groupId, user);
		UserInviteForPublicGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(UserInviteForPublicGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("User is already a member of the group", outputValues.getMessage());

	}

	@Test
	public void testUserInviteForPublicGroup_InvalidGroupId() throws Exception {
		String groupId = "group123";
		String user = "John";

		UserInviteForPublicGroupUseCase.InputValues inputValues = new UserInviteForPublicGroupUseCase.InputValues(
				groupId, user);

		UserInviteForPublicGroupUseCase.OutputValues outputValues = _useCase.execute(inputValues);

		assertEquals(UserInviteForPublicGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Invalid group ID. Unable to add user to the group", outputValues.getMessage());
	}
}