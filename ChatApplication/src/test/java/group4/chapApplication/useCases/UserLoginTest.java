package group4.chapApplication.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.adapters.Hasher;
import group4.chat.usecases.users.UserLoginUseCase;

class UserLoginTest {

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

	@Test
	void loginWithIncorrectPasswordTest() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.getUsers().add(new User("mai", "17432ef"));

		UserLoginUseCase.InputValues input = new UserLoginUseCase.InputValues("mai", "wrongpassword");
		UserLoginUseCase login = new UserLoginUseCase(storage, new MD5Hasher());

		UserLoginUseCase.OutputValues output = login.execute(input);

		assertEquals(UserLoginUseCase.ResultCodes.FAILED, output.getResultCode());
	}

	@Test
	void loginWithNonExistingUserTest() {
		DataStorage storage = InMemoryDataStorage.getInstance();

		UserLoginUseCase.InputValues input = new UserLoginUseCase.InputValues("nonexistinguser", "password");
		UserLoginUseCase login = new UserLoginUseCase(storage, new MD5Hasher());

		UserLoginUseCase.OutputValues output = login.execute(input);

		assertEquals(UserLoginUseCase.ResultCodes.FAILED, output.getResultCode());
	}

	@Test
	void loginWithHashingErrorTest() {
		DataStorage storage = InMemoryDataStorage.getInstance();
		storage.getUsers().add(new User("mai", "17432ef"));

		UserLoginUseCase.InputValues input = new UserLoginUseCase.InputValues("mai", "password");
		UserLoginUseCase login = new UserLoginUseCase(storage, new HasherWithError());

		UserLoginUseCase.OutputValues output = login.execute(input);

		assertEquals(UserLoginUseCase.ResultCodes.FAILED, output.getResultCode());
	}

	class HasherWithError implements Hasher {
		@Override
		public String hash(String input) throws Exception {
			throw new Exception("Error occurred during hashing");
		}
	}
}
