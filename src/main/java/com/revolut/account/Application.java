package com.revolut.account;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.revolut.account.core.account.AccountRepository;
import com.revolut.account.core.account.InternalAccountService;
import com.revolut.account.core.bank.BankRepository;
import com.revolut.account.core.commons.json.JsonConverter;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.core.commons.validation.Validator;
import com.revolut.account.core.customer.CustomerRepository;
import com.revolut.account.core.transaction.TransactionRepository;
import com.revolut.account.infrastructure.account.JpaAccountRepository;
import com.revolut.account.infrastructure.account.SparkAccountController;
import com.revolut.account.infrastructure.bank.JpaBankRepository;
import com.revolut.account.infrastructure.commons.json.GsonJsonConverter;
import com.revolut.account.infrastructure.commons.logger.Slf4jLazyLoggerFactory;
import com.revolut.account.infrastructure.commons.persistence.JpaInitializer;
import com.revolut.account.infrastructure.commons.server.SparkServer;
import com.revolut.account.infrastructure.commons.validation.OvalValidator;
import com.revolut.account.infrastructure.customer.JpaCustomerRepository;
import com.revolut.account.infrastructure.transaction.JpaTransactionRepository;

public class Application {

    public static final Application instance = new Application();

    private Injector injector;

    public static void main(String[] args) {

    }

    private Application() {
        initInjector();
    }

    private void initInjector() {
        injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                install(new JpaPersistModule("revolut"));

                bind(JpaInitializer.class).asEagerSingleton();
                bind(LazyLoggerFactory.class).to(Slf4jLazyLoggerFactory.class).asEagerSingleton();
                bind(Validator.class).to(OvalValidator.class).asEagerSingleton();
                bind(BankRepository.class).to(JpaBankRepository.class);
                bind(CustomerRepository.class).to(JpaCustomerRepository.class);
                bind(TransactionRepository.class).to(JpaTransactionRepository.class);
                bind(AccountRepository.class).to(JpaAccountRepository.class);
                bind(JsonConverter.class).to(GsonJsonConverter.class);
                bind(InternalAccountService.class);
                bind(SparkServer.class).asEagerSingleton();
                bind(SparkAccountController.class).asEagerSingleton();
            }
        });
    }

    public <T> T getInstance(Class<T> type) {
        return injector.getInstance(type);
    }
}
