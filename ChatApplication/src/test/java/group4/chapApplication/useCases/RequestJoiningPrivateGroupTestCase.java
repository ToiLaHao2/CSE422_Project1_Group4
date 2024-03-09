package group4.chapApplication.useCases;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.users.RequestToJoinPrivateGroupUseCase;

class RequestJoiningPrivateGroupTestCase {
	private RequestToJoinPrivateGroupUseCase _requestJoining;
	private InMemoryDataStorage _dataStorage;

	@BeforeEach
	public void setUp() {
		_dataStorage = new InMemoryDataStorage();
		_requestJoining = new RequestToJoinPrivateGroupUseCase(_dataStorage);
	}

	@Test
	void testJoinGroupSuccessfully() {
		User admin = new User("Admin1", "123");
		User user = new User("User1", "123");

		PrivateGroup group = new PrivateGroup("Group1");
		group.addAdmin(admin);

		RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
				user, group);
		RequestToJoinPrivateGroupUseCase.OutputValues outputValues = _requestJoining.execute(inputValues);

		assertEquals(true, outputValues.isRequestApproved());
	}

	@Test
	void testUserAlreadyInGroup() {
		User admin = new User("Admin1", "123");
		User user = new User("User1", "123");

		PrivateGroup group = new PrivateGroup("Group1");
		group.addAdmin(admin);
		group.addMember(user);

		RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
				user, group);
		RequestToJoinPrivateGroupUseCase.OutputValues outputValues = _requestJoining.execute(inputValues);

		assertEquals(false, outputValues.isRequestApproved());
	}

	@Test
	void testUserAlreadyRequested() {
		User admin = new User("Admin1", "123");
		User user = new User("User1", "123");

		PrivateGroup group = new PrivateGroup("Group1");
		group.addAdmin(admin);
		group.requestToJoin(user);

		RequestToJoinPrivateGroupUseCase.InputValues inputValues = new RequestToJoinPrivateGroupUseCase.InputValues(
				user, group);
		RequestToJoinPrivateGroupUseCase.OutputValues outputValues = _requestJoining.execute(inputValues);

		assertEquals(false, outputValues.isRequestApproved());
	}

}
