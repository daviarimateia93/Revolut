package com.revolut.account.core.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    private String id;
    private String name;
    @Version
    private Long version;
}
