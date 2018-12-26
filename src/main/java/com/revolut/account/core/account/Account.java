package com.revolut.account.core.account;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Account {

    @Id
    private String id;
    private String bankCode;
    private String customerId;
    @Setter(AccessLevel.PROTECTED)
    private AccountType type;
    private BigDecimal balance;
    private String number; // Although its called number, left 0 matters... so go String
    @Version
    private Long version;

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void subtractBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
