package com.revolut.account.core.transaction;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Transaction {

    @Id
    private String id;
    private BigDecimal amount;
    private String sourceAccountBankCode;
    private String sourceAccountNumber;
    private String targetAccountBankCode;
    private String targetAccountNumber;
    private ZonedDateTime dateTime;
    private TransactionType type;
    @Version
    private Long version;
}
