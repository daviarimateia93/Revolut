package com.revolut.account.infrastructure.customer;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.core.customer.Customer;
import com.revolut.account.core.customer.CustomerRepository;
import com.revolut.account.infrastructure.commons.persistence.JpaRepository;

@Transactional
public class JpaCustomerRepository extends JpaRepository<Customer, String> implements CustomerRepository {

    @Inject
    public JpaCustomerRepository(EntityManager entityManager, LazyLoggerFactory loggerFactory) {
        super(Customer.class, entityManager, loggerFactory.getLogger(JpaCustomerRepository.class));
    }
}
