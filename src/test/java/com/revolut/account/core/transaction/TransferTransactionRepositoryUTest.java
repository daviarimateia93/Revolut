package com.revolut.account.core.transaction;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.revolut.account.Application;

public class TransferTransactionRepositoryUTest {

    @Test
    public void saveAndFind() {
        TransferTransaction transferTransaction = newTransferTransaction();

        TransactionRepository transactionRepository = Application.instance.getInstance(TransactionRepository.class);
        transactionRepository.save(transferTransaction);

        Optional<TransferTransaction> foundTransferTransaction = transactionRepository
                .find(transferTransaction.getId());

        assertThat("transaction was saved", foundTransferTransaction.isPresent(), is(true));
        assertThat("found transaction is the same", transferTransaction, is(foundTransferTransaction.get()));
    }

    private TransferTransaction newTransferTransaction() {
        TransferTransaction transaction = new TransferTransaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setSourceAccountBankCode(UUID.randomUUID().toString());
        transaction.setSourceAccountNumber(UUID.randomUUID().toString());
        transaction.setTargetAccountBankCode(UUID.randomUUID().toString());
        transaction.setTargetAccountNumber(UUID.randomUUID().toString());
        transaction.setAmount(new BigDecimal("10599.99"));
        transaction.setDateTime(ZonedDateTime.now());

        return transaction;
    }
}
