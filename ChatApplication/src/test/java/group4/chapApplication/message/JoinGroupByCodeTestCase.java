package group4.chapApplication.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.users.JoinGroupByJoinCodeUseCase;

class JoinGroupByCodeTestCase {

	private InMemoryDataStorage dataStorage;
	private JoinGroupByJoinCodeUseCase useCase;

	@BeforeEach
	public void setUp() {
		dataStorage = new InMemoryDataStorage();
		useCase = new JoinGroupByJoinCodeUseCase(dataStorage);
	}

	@Test
	public void testJoinGroupByJoinCodeSuccess() throws Exception {
		String joinCode = "12345";
		String userId = "user1";
		String publicGroupId = "group1";

		User user = new User(userId, "123");
		dataStorage.getUsers().add(user);

		PublicGroup publicGroup = new PublicGroup(joinCode);
		dataStorage.getPublicGroup().add(publicGroup);

		JoinGroupByJoinCodeUseCase.InputValues inputValues = new JoinGroupByJoinCodeUseCase.InputValues(joinCode,
				userId, publicGroupId);

		JoinGroupByJoinCodeUseCase.OutputValues outputValues = useCase.execute(inputValues);

		assertEquals(JoinGroupByJoinCodeUseCase.ResultCodes.SUCCESS, outputValues.getResultCode());
		assertEquals("User has been added to the group", outputValues.getMessage());

		assertTrue(publicGroup.getGroupUsers().contains(user), "User should be added to the group");
	}

	@Test
	public void testJoinGroupByJoinCodeInvalidJoinCode() throws Exception {
		// Arrange
		String joinCode = "12345";
		String userId = "user1";
		String publicGroupId = "group1";

		User user = new User(userId, "123");
		dataStorage.getUsers().add(user);

		PublicGroup publicGroup = new PublicGroup("67890");
		dataStorage.getPublicGroup().add(publicGroup);

		JoinGroupByJoinCodeUseCase.InputValues inputValues = new JoinGroupByJoinCodeUseCase.InputValues(joinCode,
				userId, publicGroupId);

		JoinGroupByJoinCodeUseCase.OutputValues outputValues = useCase.execute(inputValues);

		assertEquals(JoinGroupByJoinCodeUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Invalid join code. Unable to join the group", outputValues.getMessage());

		assertTrue(publicGroup.getGroupUsers().isEmpty(), "User should not be added to the group");
	}

	@Test
	public void testJoinGroupByJoinCodePublicGroupNotFound() throws Exception {
		// Arrange
		String joinCode = "12345";
		String userId = "user1";
		String publicGroupId = "nonexistentGroup";

		User user = new User(userId, "123");
		dataStorage.getUsers().add(user);

		JoinGroupByJoinCodeUseCase.InputValues inputValues = new JoinGroupByJoinCodeUseCase.InputValues(joinCode,
				userId, publicGroupId);

		JoinGroupByJoinCodeUseCase.OutputValues outputValues = useCase.execute(inputValues);

		assertEquals(JoinGroupByJoinCodeUseCase.ResultCodes.FAILED, outputValues.getResultCode());
		assertEquals("Invalid join code. Unable to join the group", outputValues.getMessage());
	}

}
