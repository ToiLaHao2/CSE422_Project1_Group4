package group4.chapApplication.useCases;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.UserLoginUseCase;

class UserLoginTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.getUsers().add(new User("mai", "17432ef"));
	}

	@AfterEach
	public void tearDown() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.cleanAll();
	}

	@Test
	void loginForUsertest() {
		UserLoginUseCase.InputValues input = new UserLoginUseCase.InputValues("mai", "1743ef");
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserLoginUseCase login = new UserLoginUseCase(storage, new MD5Hasher());
		UserLoginUseCase.OutputValues output = login.execute(input);
	}
}
