package com.allaroundjava.dao;

import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(Long id);

    void persist(T item);

    void merge(T item);

    void delete(T item);

    void refersh(T item);
}
