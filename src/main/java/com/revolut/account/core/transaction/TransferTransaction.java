package com.revolut.account.core.transaction;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TransferTransaction extends Transaction {

    public TransferTransaction() {
        setType(TransactionType.TRANSFER);
    }
}
