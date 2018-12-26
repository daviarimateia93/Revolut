package com.revolut.account.core.account;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.revolut.account.Application;
import com.revolut.account.core.bank.RevolutBank;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.core.commons.persistence.MockedRepository;
import com.revolut.account.core.transaction.Transaction;
import com.revolut.account.core.transaction.TransactionRepository;
import com.revolut.account.core.transaction.TransactionType;

public class InternalAccountServiceUTest {

    private AccountRepository accountRepository = new MockedAccountRepository();
    private TransactionRepository transactionRepository = new MockedTransactionRepository();

    private InternalAccountService accountService = new InternalAccountService(
            Application.instance.getInstance(LazyLoggerFactory.class),
            accountRepository,
            transactionRepository);

    @Test
    public void transfer() {
        BigDecimal amount = new BigDecimal("15.54");
        CurrentAccount sourceAccount = newCurrentAccount();
        CurrentAccount targetAccount = newCurrentAccount();

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        Transaction transaction = accountService.transfer(amount, sourceAccount.getNumber(), targetAccount.getNumber());

        assertThat("returned transaction is not null", transaction, not(nullValue()));
        assertThat("returned transaction type is transfer", transaction.getType(), is(TransactionType.TRANSFER));
        assertThat("returned transaction amount is right", transaction.getAmount(), is(amount));

        assertThat("returned transaction source account bank is right",
                transaction.getSourceAccountBankCode(),
                is(sourceAccount.getBankCode()));

        assertThat("returned transaction source account number is right",
                transaction.getSourceAccountNumber(),
                is(sourceAccount.getNumber()));

        assertThat("returned transaction target account bank is right",
                transaction.getTargetAccountBankCode(),
                is(targetAccount.getBankCode()));

        assertThat("returned transaction target account number is right",
                transaction.getTargetAccountNumber(),
                is(targetAccount.getNumber()));
    }

    private CurrentAccount newCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBankCode(RevolutBank.CODE);
        currentAccount.setNumber("my number");
        currentAccount.setCustomerId(UUID.randomUUID().toString());
        currentAccount.setBalance(new BigDecimal("125000.00"));

        return currentAccount;
    }

    private class MockedAccountRepository extends MockedRepository<Account, String> implements AccountRepository {

        @Override
        public <T extends Account> Optional<T> find(String id) {
            return cast(getEntities().stream().filter(e -> e.getId().equals(id)).findFirst());
        }

        @Override
        public <T extends Account> Optional<T> findByNumberAndBankCode(String number, String bankCode) {
            return cast(getEntities()
                    .stream()
                    .filter(e -> e.getNumber().equals(number) && e.getBankCode().equals(bankCode))
                    .findFirst());
        }
    }

    private class MockedTransactionRepository
            extends MockedRepository<Transaction, String>
            implements TransactionRepository {

        @Override
        public <T extends Transaction> Optional<T> find(String id) {
            return cast(getEntities().stream().filter(e -> e.getId().equals(id)).findFirst());
        }
    }
}
