package group4.chapApplication.useCases;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.users.UserInviteForPublicGroupUseCase;

class InvitePublicGroupTestCase {
	private InMemoryDataStorage _dataStorage;
	private UserInviteForPublicGroupUseCase _userInviteForPublicGroupUseCase;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_userInviteForPublicGroupUseCase = new UserInviteForPublicGroupUseCase(_dataStorage);
	}

	@Test
	public void testUserInviteForPublicGroup_Success() throws Exception {
		String joinCode = "group123";
		String user = "John";
		String groupId = "Mai123";
		String userId = "Mai123";
		
		PublicGroup publicGroup = new PublicGroup(joinCode);
		publicGroup.setId(groupId);
		
		User userUser = new User(user, "123");
		userUser.setId(userId);
		
		_dataStorage.getPublicGroup().add(publicGroup);
		_dataStorage.getUsers().add(userUser);
		
		UserInviteForPublicGroupUseCase.InputValues inputValues = new UserInviteForPublicGroupUseCase.InputValues(
				groupId, userId);

		UserInviteForPublicGroupUseCase.OutputValues outputValues = _userInviteForPublicGroupUseCase.execute(inputValues);
	

		assertEquals(UserInviteForPublicGroupUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User has been added to the group", outputValues.getResultCode(), outputValues.getResultCode());
		assertEquals(1, publicGroup.getGroupUsers().size());
		assertEquals(userUser, publicGroup.getGroupUsers().get(0));
	}

	@Test
	public void testUserInviteForPublicGroup_UserAlreadyMember() throws Exception {
		String joinCode = "group123";
		String user = "John";
		String groupId = "Mai123";
		String userId = "Mai123";
		
		User userUser = new User(user, "123");
		userUser.setId(userId);
		
		PublicGroup publicGroup = new PublicGroup(joinCode);
		publicGroup.setId(groupId);
		publicGroup.addMember(userUser);
		
		_dataStorage.getUsers().add(userUser);
		_dataStorage.getPublicGroup().add(publicGroup);

		UserInviteForPublicGroupUseCase.InputValues inputValues = new UserInviteForPublicGroupUseCase.InputValues(
				groupId, userId);

		UserInviteForPublicGroupUseCase.OutputValues outputValues = _userInviteForPublicGroupUseCase.execute(inputValues);

		assertEquals(UserInviteForPublicGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("User is already a member of the group", outputValues.getMessage());

	}

	@Test
	public void testUserInviteForPublicGroup_InvalidGroupId() throws Exception {
		String joinCode = "group123";
		String user = "John";
		String groupId = "Mai123";
		String groupIdTest = "Mai456";
		String userId = "Mai123";
		
		User userUser = new User(user, "123");
		userUser.setId(userId);
		
		PublicGroup publicGroup = new PublicGroup(joinCode);
		publicGroup.setId(groupId);
		publicGroup.addMember(userUser);
		
		_dataStorage.getUsers().add(userUser);
		_dataStorage.getPublicGroup().add(publicGroup);

		UserInviteForPublicGroupUseCase.InputValues inputValues = new UserInviteForPublicGroupUseCase.InputValues(
				groupIdTest, userId);

		UserInviteForPublicGroupUseCase.OutputValues outputValues = _userInviteForPublicGroupUseCase.execute(inputValues);

		assertEquals(UserInviteForPublicGroupUseCase.ResultCodes.FAILED, outputValues.getResultCode());
	}
}
