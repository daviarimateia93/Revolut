package com.revolut.account.core.customer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.revolut.account.Application;

public class CustomerRepositoryUTest {

    @Test
    public void saveAndFind() {
        Customer customer = newCustomer();

        CustomerRepository customerRepository = Application.instance.getInstance(CustomerRepository.class);
        customerRepository.save(customer);

        Optional<Customer> foundCustomer = customerRepository.find(customer.getId());

        assertThat("customer was saved", foundCustomer.isPresent(), is(true));
        assertThat("found customer is the same", customer, is(foundCustomer.get()));
    }

    private Customer newCustomer() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setName("Revolut Customer");
        return customer;
    }
}
