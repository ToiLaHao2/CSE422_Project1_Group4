package group4.chat.usecases.adapters;
import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;

public interface DataStorage {
   Respository<User> getUsers();
   Respository<PublicGroup> getPubicGroup();
   Respository<PrivateGroup> getPrivateGroup();
   void cleanAll();
}


