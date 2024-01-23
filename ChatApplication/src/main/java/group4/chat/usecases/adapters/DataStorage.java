package group4.chat.usecases.adapters;
import group4.chat.domains.User;

public interface DataStorage {
   Respository<User> getUsers();
   void cleanAll();
}


