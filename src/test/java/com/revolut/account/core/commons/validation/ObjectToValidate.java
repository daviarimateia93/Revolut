package com.revolut.account.core.commons.validation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ObjectToValidate {

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Min(0)
    private Integer age;
}
