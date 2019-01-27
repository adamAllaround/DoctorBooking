package com.allaroundjava.dao;

import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(Long id);

    void persist(T item);

    void merge(T item);
}
