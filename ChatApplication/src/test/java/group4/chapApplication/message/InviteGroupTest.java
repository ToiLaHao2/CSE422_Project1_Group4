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
	}

	@AfterEach
	public void tearDown() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.cleanAll();
	}

	@Test
	public void addUserSuccessfully() throws Exception {

		UserRegistrationUseCase.InputValues input = new UserRegistrationUseCase.InputValues("phuc", "1234");
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserRegistrationUseCase registration = new UserRegistrationUseCase(storage, new MD5Hasher());
		UserRegistrationUseCase.OutputValues output = registration.execute(input);

		input = new UserRegistrationUseCase.InputValues("phuccc", "1234");
		storage = InMemoryDataStorage.getInstance();

		registration = new UserRegistrationUseCase(storage, new MD5Hasher());
		output = registration.execute(input);

		UserInviteUseCase invite = new UserInviteUseCase("phuc", "1234");
		
	}

}
