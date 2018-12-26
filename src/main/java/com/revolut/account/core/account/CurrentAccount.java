package com.revolut.account.core.account;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CurrentAccount extends Account {

    public CurrentAccount() {
        setType(AccountType.CURRENT);
    }
}
