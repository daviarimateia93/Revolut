package com.revolut.account.infrastructure.bank;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;
import com.revolut.account.core.bank.Bank;
import com.revolut.account.core.bank.BankRepository;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.infrastructure.commons.persistence.JpaRepository;

@Transactional
public class JpaBankRepository extends JpaRepository<Bank, String> implements BankRepository {

    @Inject
    public JpaBankRepository(EntityManager entityManager, LazyLoggerFactory loggerFactory) {
        super(Bank.class, entityManager, loggerFactory.getLogger(JpaBankRepository.class));
    }
}
