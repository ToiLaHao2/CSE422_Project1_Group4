package group4.chapApplication.message;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.CreatePrivateGroupUseCase;
import group4.chat.usecases.users.UserInviteForPrivateGroupUseCase;

class TestCreatePrivateGroup {

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

//	@Test
//	public void createPrivateGroupTest() throws Exception {
//		CreatePrivateGroupUseCase.InputValues input = new CreatePrivateGroupUseCase.InputValues("phucc", "haoo");
//		DataStorage storage = InMemoryDataStorage.getInstance();
//
//		UserInviteForPrivateGroupUseCase invite = new UserInviteForPrivateGroupUseCase(storage);
//		UserInviteForPrivateGroupUseCase.OutputValues output = invite.execute(input);
//	}

}
