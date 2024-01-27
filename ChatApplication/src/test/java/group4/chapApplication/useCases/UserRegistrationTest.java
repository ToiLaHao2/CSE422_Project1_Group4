package group4.chapApplication.useCases;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.UserRegistration;

import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.users.UserRegistration.OutputValues;
import group4.chat.usecases.users.UserRegistration.ResultCodes;

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
    public void addUserSuccessfully() throws Exception {

        UserRegistration.InputValues input = new UserRegistration.InputValues("phuc", "1234");
        DataStorage storage = InMemoryDataStorage.getInstance();

        UserRegistration registration = new UserRegistration(storage, new MD5Hasher());
        UserRegistration.OutputValues output = registration.execute(input);

    }

}
