package group4.chapApplication.message;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.LeaveGroupUseCase;
import group4.chat.usecases.users.UserInviteUseCase;
import group4.chat.usecases.users.UserRegistrationUseCase;

class LeavvePrivateGroupTest {

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
	public void inviteUserToGroupTest() throws Exception {
		UserInviteUseCase.InputValues input = new UserInviteUseCase.InputValues("phucc", "haoo", "0001248");
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserInviteUseCase invite = new UserInviteUseCase(storage, new MD5Hasher());
		UserInviteUseCase.OutputValues output = invite.execute(input);
		
	}

}
