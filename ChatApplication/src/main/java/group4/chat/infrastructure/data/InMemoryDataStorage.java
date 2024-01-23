/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group4.chat.infrastructure.data;

import group4.chat.domains.User;
import group4.chat.infrastructure.repositories.InMemoryRepositories;
import group4.chat.usecases.adapters.*;

/**
 *
 * @author Asus
 */
public class InMemoryDataStorage implements DataStorage {
    private Respository<User> _users;

    private static InMemoryDataStorage storage;

    private InMemoryDataStorage() {
        _users = new InMemoryRepositories<User>();
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
    }
}
