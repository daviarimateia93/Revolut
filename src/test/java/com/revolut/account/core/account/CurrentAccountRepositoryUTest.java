package com.revolut.account.core.account;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.revolut.account.Application;
import com.revolut.account.core.bank.RevolutBank;

public class CurrentAccountRepositoryUTest {

    @Test
    public void saveAndFind() {
        CurrentAccount currentAccount = newCurrentAccount();

        AccountRepository accountRepository = Application.instance.getInstance(AccountRepository.class);
        accountRepository.save(currentAccount);

        Optional<CurrentAccount> foundCurrentAccount = accountRepository.find(currentAccount.getId());

        assertThat("current account was saved", foundCurrentAccount.isPresent(), is(true));
        assertThat("found account is the same", currentAccount, is(foundCurrentAccount.get()));
    }

    public void findByIdAndBankId() {
        CurrentAccount currentAccount = newCurrentAccount();

        AccountRepository accountRepository = Application.instance.getInstance(AccountRepository.class);
        accountRepository.save(currentAccount);

        Optional<CurrentAccount> foundCurrentAccount = accountRepository
                .findByNumberAndBankCode(currentAccount.getNumber(), currentAccount.getBankCode());

        assertThat("current account was saved", foundCurrentAccount.isPresent(), is(true));
        assertThat("found account is the same", currentAccount, is(foundCurrentAccount.get()));
    }

    private CurrentAccount newCurrentAccount() {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBankCode(RevolutBank.CODE);
        currentAccount.setCustomerId(UUID.randomUUID().toString());
        currentAccount.setBalance(new BigDecimal("125000.00"));
        currentAccount.setNumber("01234");

        return currentAccount;
    }
}
