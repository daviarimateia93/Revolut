package com.revolut.account.infrastructure.account;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class InternalTransferDTO {

    @NotNull
    @DecimalMin(inclusive = false, value = "0")
    private BigDecimal amount;

    @NotNull
    @NotBlank
    private String sourceAccountNumber;

    @NotNull
    @NotBlank
    private String targetAccountNumber;
}
