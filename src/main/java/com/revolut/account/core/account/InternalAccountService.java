package com.revolut.account.core.account;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.inject.Inject;

import com.google.inject.persist.Transactional;
import com.revolut.account.core.bank.RevolutBank;
import com.revolut.account.core.commons.exception.BusinessException;
import com.revolut.account.core.commons.logger.LazyLogger;
import com.revolut.account.core.commons.logger.LazyLoggerFactory;
import com.revolut.account.core.transaction.Transaction;
import com.revolut.account.core.transaction.TransactionRepository;
import com.revolut.account.core.transaction.TransferTransaction;

/*
 * It is named as Internal, so we suppose here goes only internal operations from Revolut Bank to Revolut Bank
 */
public class InternalAccountService {

    private LazyLogger logger;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Inject
    public InternalAccountService(
            LazyLoggerFactory loggerFactory,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {

        this.logger = loggerFactory.getLogger(InternalAccountService.class);
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private Account findByNumber(String number) {
        return accountRepository.findByNumberAndBankCode(number, RevolutBank.CODE)
                .orElseThrow(() -> new BusinessException("there is no Revolut account with number %s", number));
    }

    @Transactional
    public TransferTransaction transfer(BigDecimal amount, String sourceNumber, String targetNumber) {
        logger.info(() -> String.format("transfering %s from %s to %s", amount, sourceNumber, targetNumber));

        withdrawWithoutTransaction(amount, sourceNumber);
        depositWithoutTransaction(amount, targetNumber);

        TransferTransaction transaction = newTransferTransaction(
                amount,
                RevolutBank.CODE,
                sourceNumber,
                RevolutBank.CODE,
                targetNumber);

        logger.info(() -> String.format("generated transaction %s", transaction));

        return transaction;
    }

    /*
     * In the future, we could have withdraw() method which returns an
     * WithdrawTransaction
     */
    private void withdrawWithoutTransaction(BigDecimal amount, String number) {
        logger.info(() -> String.format("withdrawing %s from account number %s", amount, number));

        Account account = findByNumber(number);
        account.subtractBalance(amount);

        accountRepository.save(account);
    }

    /*
     * In the future, we could have deposit() method which returns an
     * DepositTransaction
     */
    private void depositWithoutTransaction(BigDecimal amount, String number) {
        logger.info(() -> String.format("depositing %s from account number %s", amount, number));

        Account account = findByNumber(number);
        account.addBalance(amount);

        accountRepository.save(account);
    }

    private TransferTransaction newTransferTransaction(
            BigDecimal amount,
            String sourceBankCode,
            String sourceNumber,
            String targetBankCode,
            String targetNumber) {

        return newTransaction(
                new TransferTransaction(),
                amount,
                sourceBankCode,
                sourceNumber,
                targetBankCode,
                targetNumber);
    }

    private <T extends Transaction> T newTransaction(
            T transaction,
            BigDecimal amount,
            String sourceBankCode,
            String sourceNumber,
            String targetBankCode,
            String targetNumber) {

        transaction.setId(UUID.randomUUID().toString());
        transaction.setSourceAccountBankCode(sourceBankCode);
        transaction.setSourceAccountNumber(sourceNumber);
        transaction.setTargetAccountBankCode(targetBankCode);
        transaction.setTargetAccountNumber(targetNumber);
        transaction.setAmount(amount);
        transaction.setDateTime(ZonedDateTime.now());

        return transactionRepository.save(transaction);
    }
}
