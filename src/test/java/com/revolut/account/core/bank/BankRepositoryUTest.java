package com.revolut.account.core.bank;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.revolut.account.Application;

public class BankRepositoryUTest {

    @Test
    public void saveAndFind() {
        Bank bank = newBank();

        BankRepository bankRepository = Application.instance.getInstance(BankRepository.class);
        bankRepository.save(bank);

        Optional<Bank> foundBank = bankRepository.find(bank.getId());

        assertThat("bank was saved", foundBank.isPresent(), is(true));
        assertThat("found bank is the same", bank, is(foundBank.get()));
    }

    private Bank newBank() {
        return new RevolutBank();
    }
}
