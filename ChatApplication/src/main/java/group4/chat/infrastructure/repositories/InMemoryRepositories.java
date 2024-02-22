/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package group4.chat.infrastructure.repositories;

import group4.chat.usecases.adapters.Respository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import group4.chat.domains.BaseEntity;

/**
 *
 * @author Asus
 */
public class InMemoryRepositories<T extends BaseEntity> implements Respository<T> {
    private List<T> enities;
    public static int idCounter = 1;

    public InMemoryRepositories() {
        enities = new ArrayList<>();
    }

    @Override
    public T getById(String id) {
        Optional<T> entity = enities.stream().filter(e -> e.getId().equals(id)).findFirst();
        if (entity.isEmpty()) {
            return null;
        } else
            return entity.get();
    }

    @Override
    public boolean add(T entity) {

        if (entity == null) {
            return false;
        }

        enities.add(entity);
        return true;
    }

    @Override
    public void deleteAll() {
        enities.clear();
    }

    @Override
    public T getFirst(Predicate<T> predicate) {
        Optional<T> entity = enities.stream().filter(predicate).findFirst();
        return entity.isPresent() ? entity.get() : null;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(enities);
    }

}
