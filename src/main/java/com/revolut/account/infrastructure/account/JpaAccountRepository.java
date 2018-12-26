package com.revolut.account.infrastructure.account;

import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.persist.Transactional;
import com.revolut.account.core.account.Account;
import com.revolut.account.core.account.AccountRepository;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.infrastructure.commons.persistence.JpaRepository;

@Transactional
public class JpaAccountRepository extends JpaRepository<Account, String> implements AccountRepository {

    @Inject
    public JpaAccountRepository(EntityManager entityManager, LazyLoggerFactory loggerFactory) {
        super(Account.class, entityManager, loggerFactory.getLogger(JpaAccountRepository.class));
    }

    @Override
    public <T extends Account> Optional<T> findByNumberAndBankCode(String number, String bankCode) {
        TypedQuery<Account> typedQuery = getEntityManager()
                .createQuery("SELECT a FROM Account a WHERE a.number = :number AND a.bankCode = :bankCode",
                        Account.class);

        typedQuery.setParameter("number", number);
        typedQuery.setParameter("bankCode", bankCode);

        return getResult(typedQuery);
    }
}
