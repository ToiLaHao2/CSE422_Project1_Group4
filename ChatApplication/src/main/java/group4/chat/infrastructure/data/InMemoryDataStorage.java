/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group4.chat.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import group4.chat.domains.User;
import group4.chat.domains.groupUser.privateGroup.PrivateGroup;
import group4.chat.domains.groupUser.publicGroup.PublicGroup;
import group4.chat.infrastructure.repositories.InMemoryRepositories;
import group4.chat.message.Conversation;
import group4.chat.usecases.adapters.*;

/**
 *
 * @author Asus
 */
public class InMemoryDataStorage implements DataStorage {
    private Respository<User> _users;
    private Respository<PublicGroup> _publicGroups;
    private Respository<PrivateGroup> _privateGroups;
    private Map<String, Conversation> _conversations;

    private static InMemoryDataStorage storage;

    public InMemoryDataStorage() {
        _users = new InMemoryRepositories<User>();
        _publicGroups = new InMemoryRepositories<PublicGroup>();
        _privateGroups = new InMemoryRepositories<PrivateGroup>();
         _conversations = new HashMap<>();
    }

    public static InMemoryDataStorage getInstance() {
        if (storage == null) {
            storage = new InMemoryDataStorage();
        }
        return storage;
    }

    public Respository<User> getUsers() {
        return _users;
    }

    @Override
    public void cleanAll() {
        _users.deleteAll();
        _publicGroups.deleteAll();
        _privateGroups.deleteAll();
        _conversations.clear();
    }

    public Respository<PublicGroup> getPublicGroup() {
        return _publicGroups;
    }

    public Respository<PrivateGroup> getPrivateGroup() {
        return _privateGroups;
    }
    @Override
    public List<User> getAllUsers() {
        return _users.getAll();
    }
     public List<Conversation> getAllConversations() {
        return new ArrayList<>(_conversations.values());
    }
    public Conversation getConversation(String conversationId) {
        return _conversations.get(conversationId);
    }

    
}
