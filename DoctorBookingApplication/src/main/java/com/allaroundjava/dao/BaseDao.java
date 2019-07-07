package com.allaroundjava.dao;

import com.allaroundjava.model.ModelBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

abstract class BaseDao<T extends ModelBase> implements Dao<T> {
    private static final Logger log = LogManager.getLogger(BaseDao.class);
    @PersistenceContext
    EntityManager entityManager;
    private final Class<T> aClass;

    BaseDao(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public Optional<T> getById(Long id) {
        log.debug("Fetching {} with id {} from database", getClass(), id);
        return Optional.ofNullable(entityManager.find(aClass, id));
    }

    @Override
    public void persist(T item) {
        log.debug("Persisting {} with id {}",
                item.getClass(), item.getId());
        entityManager.persist(item);
    }

    @Override
    public void merge(T item) {
        entityManager.merge(item);
    }

    @Override
    public void delete(T item) {
        log.debug("Deleting {} with id {}",
                item.getClass(), item.getId());
        T retrievedItem = entityManager.find(aClass, item.getId());
        entityManager.remove(retrievedItem);
    }

    @Override
    public void refersh(T item) {
        entityManager.refresh(item);
    }

    Optional<T> getSingleAsOptional(TypedQuery<T> query) {
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
