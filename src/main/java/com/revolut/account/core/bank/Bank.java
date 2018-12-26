package com.revolut.account.core.bank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PROTECTED)
@Entity
public class Bank {

    @Id
    private String id;
    private String name;
    private String code;
    @Version
    private Long version;
}
