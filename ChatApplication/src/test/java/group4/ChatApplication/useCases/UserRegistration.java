import java.security.Identity;
import java.util.Optional;

//import org.junit.jupiter.api.Assertions;
// org.junit.jupiter.api.Test;

import group4.chat.usecases.adapters.DataStorage;
import group4.chat.usecases.users.UserRegistration.InputValues;
import group4.chat.domains.User;
import group4.chat.infrastructure.data.InMemoryDataStorage;
import group4.chat.infrastructure.services.MD5Hasher;
import group4.chat.usecases.users.UserRegistration.OutputValues;
import group4.chat.usecases.users.UserRegistration.ResultCodes;

public class UserRegistration {
   // @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        DataStorage storage = InMemoryDataStorage.getInstance();
        storage.getUsers().add(new User("phuc", "1234"));
    }

    //@org.junit.jupiter.api.AfterEach
    public void tearDown() {
        DataStorage storage = InMemoryDataStorage.getInstance();
        storage.cleanAll();
    }

    

    @Test
    public void addUserSuccessfully() {

        //UserRegistration. input = new UserRegistration.InputValues("phuc", "1234");
        //DataStorage storage = InMemoryDataStorage.getInstance();

        //UserRegistration registration = new UserRegistration(storage, new MD5Hasher());
       // UserRegistration.OutputValues output = registration.execute(input);

        
    }



   
}
