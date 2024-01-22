package group4.ChatApplication.Usecase.adapters;
import group4.chat.domains.User;

public interface DataStorage {
    
   Respository<User> getUser();

   void cleanAll();
}


