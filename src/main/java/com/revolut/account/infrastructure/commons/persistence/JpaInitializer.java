package com.revolut.account.infrastructure.commons.persistence;

import javax.inject.Inject;

import com.google.inject.persist.PersistService;

public class JpaInitializer {

    @Inject
    public JpaInitializer(PersistService persistService) {
        persistService.start();
    }
}
