package com.revolut.account.infrastructure.transaction;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.core.transaction.Transaction;
import com.revolut.account.core.transaction.TransactionRepository;
import com.revolut.account.infrastructure.commons.persistence.JpaRepository;

@Transactional
public class JpaTransactionRepository extends JpaRepository<Transaction, String> implements TransactionRepository {

    @Inject
    public JpaTransactionRepository(EntityManager entityManager, LazyLoggerFactory loggerFactory) {
        super(Transaction.class, entityManager, loggerFactory.getLogger(JpaTransactionRepository.class));
    }
}
