package com.revolut.account.core.account;

import java.util.Optional;

import com.revolut.account.core.commons.persistence.Repository;

public interface AccountRepository extends Repository<Account, String> {

    <T extends Account> Optional<T> findByNumberAndBankCode(String number, String bankCode);
}
