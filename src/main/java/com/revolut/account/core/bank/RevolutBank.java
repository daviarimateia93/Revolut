package com.revolut.account.core.bank;

import javax.persistence.Entity;

@Entity
public class RevolutBank extends Bank {

    public static final String ID = "123123142414";
    public static final String CODE = "01231";
    public static final String NAME = "Revolut Bank";

    public RevolutBank() {
        super();

        setId(ID);
        setCode(CODE);
        setName(NAME);
    }
}
