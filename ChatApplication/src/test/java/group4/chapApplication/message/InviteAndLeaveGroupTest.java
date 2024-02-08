package group4.chapApplication.message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.LeaveGroupUseCase;
import group4.chat.usecases.users.UserInviteForPrivateGroupUseCase;
import group4.chat.usecases.users.UserInviteForPublicGroupUseCase;

class InviteAndLeaveGroupTest {

	@BeforeEach
	public void setUp() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.getUsers().add(new User("phuc", "1234"));
		storage.getUsers().add(new User("haoo", "1234"));
	}

	@AfterEach
	public void tearDown() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.cleanAll();
	}

	@Test
	public void inviteUserToPrivateGroupTest() throws Exception {
		UserInviteForPrivateGroupUseCase.InputValues input = new UserInviteForPrivateGroupUseCase.InputValues("phucc",
				"haoo", "0001248");
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserInviteForPrivateGroupUseCase invite = new UserInviteForPrivateGroupUseCase(storage, new MD5Hasher());
		UserInviteForPrivateGroupUseCase.OutputValues output = invite.execute(input);
	}

	@Test
	public void inviteUserToPublicGroupTest() throws Exception {
		UserInviteForPublicGroupUseCase.InputValues input = new UserInviteForPublicGroupUseCase.InputValues("phucc",
				"11111009");
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserInviteForPublicGroupUseCase invite = new UserInviteForPublicGroupUseCase(storage, new MD5Hasher());
		UserInviteForPublicGroupUseCase.OutputValues output = invite.execute(input);
	}

	@Test
	public void leaveGroupTest() throws Exception {
		LeaveGroupUseCase.InputValues input = new LeaveGroupUseCase.InputValues("phucc", "haoo", "0001248");
		DataStorage storage = InMemoryDataStorage.getInstance();

		LeaveGroupUseCase leave = new LeaveGroupUseCase(storage, new MD5Hasher());
		LeaveGroupUseCase.OutputValues output = leave.execute(input);
	}

}
