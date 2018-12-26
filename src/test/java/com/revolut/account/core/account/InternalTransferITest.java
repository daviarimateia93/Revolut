package com.revolut.account.core.account;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.revolut.account.Application;
import com.revolut.account.core.bank.RevolutBank;
import com.revolut.account.core.commons.json.JsonConverter;
import com.revolut.account.core.transaction.Transaction;
import com.revolut.account.core.transaction.TransactionRepository;
import com.revolut.account.core.transaction.TransactionType;
import com.revolut.account.core.transaction.TransferTransaction;
import com.revolut.account.infrastructure.account.InternalTransferDTO;

public class InternalTransferITest {

    private static final String ENDPOINT = "http://localhost:4567/accounts/transfer/internal";

    private JsonConverter jsonConverter = Application.instance.getInstance(JsonConverter.class);

    @Test
    @Ignore
    public void transferWithNullPayload() throws UnirestException {
        HttpResponse<String> response = Unirest
                .post(ENDPOINT)
                .body("")
                .asString();

        assertThat("post request returns status 400", response.getStatus(), is(400));
    }

    @Test
    @Ignore
    public void transferWithInvalidlPayload() throws UnirestException {
        InternalTransferDTO dto = new InternalTransferDTO();
        dto.setAmount(new BigDecimal("15"));

        HttpResponse<String> response = Unirest
                .post(ENDPOINT)
                .body(jsonConverter.toJson(dto))
                .asString();

        assertThat("post request returns status 400", response.getStatus(), is(400));
    }

    @Test
    public void transfer() throws UnirestException {
        BigDecimal amount = new BigDecimal("65.03");
        CurrentAccount sourceAccount = newCurrentAccount();
        CurrentAccount targetAccount = newCurrentAccount();

        InternalTransferDTO dto = new InternalTransferDTO();
        dto.setAmount(amount);
        dto.setSourceAccountNumber(sourceAccount.getNumber());
        dto.setTargetAccountNumber(targetAccount.getNumber());

        AccountRepository accountRepository = Application.instance.getInstance(AccountRepository.class);
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        HttpResponse<String> response = Unirest
                .post(ENDPOINT)
                .body(jsonConverter.toJson(dto))
                .asString();

        assertThat("post request returns status 200", response.getStatus(), is(200));

        TransferTransaction transaction = jsonConverter.toObject(response.getBody(), TransferTransaction.class);

        TransactionRepository transactionRepository = Application.instance.getInstance(TransactionRepository.class);

        Optional<CurrentAccount> foundSourceAccount = accountRepository.find(sourceAccount.getId());
        Optional<CurrentAccount> foundTargetAccount = accountRepository.find(targetAccount.getId());
        Optional<Transaction> foundTransaction = transactionRepository.find(transaction.getId());

        assertThat("returned transaction is not null", transaction, not(nullValue()));
        assertThat("source account was saved", foundSourceAccount.isPresent(), is(true));
        assertThat("found source account is the same", foundSourceAccount.get(), is(sourceAccount));
        assertThat("target account was saved", foundTargetAccount.isPresent(), is(true));
        assertThat("found target account is the same", foundTargetAccount.get(), is(targetAccount));
        assertThat("returned transaction was saved", foundTransaction.isPresent(), is(true));
        assertThat("found transaction is the same", foundTransaction.get(), is(transaction));
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
        currentAccount.setNumber(UUID.randomUUID().toString());
        currentAccount.setCustomerId(UUID.randomUUID().toString());
        currentAccount.setBalance(new BigDecimal("125000.00"));

        return currentAccount;
    }
}
