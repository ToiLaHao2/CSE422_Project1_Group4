package group4.chat.usecases.adapters;
import java.util.List;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.message.Conversation;

public interface DataStorage {
   Respository<User> getUsers();
   Respository<PublicGroup> getPublicGroup();
   Respository<PrivateGroup> getPrivateGroup();
   List<User> getAllUsers();
   Conversation getConversation(String conversationId);
   void cleanAll();
}


