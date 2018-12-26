package com.revolut.account.core.commons.persistence;

import java.util.Optional;

public interface Repository<E, U> {

    <T extends E> Optional<T> find(U id);

    <T extends E> T save(E entity);
}
