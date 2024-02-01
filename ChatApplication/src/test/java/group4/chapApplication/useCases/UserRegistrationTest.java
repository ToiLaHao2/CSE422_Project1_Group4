package group4.chapApplication.useCases;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.UserRegistrationUseCase;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.users.UserRegistrationUseCase.OutputValues;
import group4.chat.usecases.users.UserRegistrationUseCase.ResultCodes;

public class UserRegistrationTest {
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
	public void createUserSuccessfully() throws Exception {
		UserRegistrationUseCase.InputValues input = new UserRegistrationUseCase.InputValues("phuc", "1234");
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserRegistrationUseCase registration = new UserRegistrationUseCase(storage, new MD5Hasher());
		UserRegistrationUseCase.OutputValues output = registration.execute(input);
	}
}
