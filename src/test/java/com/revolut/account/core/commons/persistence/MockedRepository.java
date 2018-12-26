package com.revolut.account.core.commons.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MockedRepository<E, U> implements Repository<E, U> {

    private List<E> entities = new ArrayList<>();

    protected List<E> getEntities() {
        return entities;
    }

    @SuppressWarnings("unchecked")
    protected <T extends E> Optional<T> cast(Optional<E> entity) {
        return (Optional<T>) entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends E> T save(E entity) {
        this.entities.add(entity);

        return (T) entity;
    }
}
