package group4.chat.usecases.adapters;

import java.util.function.Predicate;
import group4.chat.domains.BaseEntity;

public interface Respository<T extends BaseEntity> {
    T getById(String id);

    boolean add(T addingEntity);

   void deleteAll();

T getFirst(Predicate<T> predicate);
} 


