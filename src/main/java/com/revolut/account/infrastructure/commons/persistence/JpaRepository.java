package com.revolut.account.infrastructure.commons.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.persist.Transactional;
import com.revolut.account.core.commons.logger.LazyLogger;
import com.revolut.account.core.commons.persistence.Repository;

@Transactional
public class JpaRepository<E, U> implements Repository<E, U> {

    private Class<E> type;
    private EntityManager entityManager;
    private LazyLogger logger;

    public JpaRepository(Class<E> type, EntityManager entityManager, LazyLogger logger) {
        this.type = type;
        this.entityManager = entityManager;
        this.logger = logger;
    }

    protected Class<E> getType() {
        return type;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected LazyLogger getLogger() {
        return logger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends E> Optional<T> find(U id) {
        logger.debug(() -> String.format("finding %s with id %s", type, id));

        return (Optional<T>) Optional.ofNullable(entityManager.find(type, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends E> T save(E entity) {
        logger.debug(() -> String.format("saving %s - %s", type, entity));

        U id = getId(entity);

        return (T) find(id)
                .map(entityManager::merge)
                .orElseGet(() -> {
                    entityManager.persist(entity);

                    return entity;
                });
    }

    @SuppressWarnings("unchecked")
    protected U getId(E entity) {
        return (U) getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
    }

    @SuppressWarnings("unchecked")
    protected <T extends E> List<T> getResults(Query query) {
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    protected <T extends E> Optional<T> getResult(Query query) {
        return (Optional<T>) getResults(query)
                .stream()
                .findFirst();
    }
}
