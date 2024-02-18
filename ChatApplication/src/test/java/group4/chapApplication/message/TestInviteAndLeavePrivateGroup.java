package group4.chapApplication.message;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.UserInviteForPrivateGroupUseCase;

class TestInviteAndLeavePrivateGroup {

	@BeforeEach
	public void setUp() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.getUsers().add(new User("phucc", "1234"));
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

		UserInviteForPrivateGroupUseCase invite = new UserInviteForPrivateGroupUseCase(storage);
		UserInviteForPrivateGroupUseCase.OutputValues output = invite.execute(input);
	}

}
