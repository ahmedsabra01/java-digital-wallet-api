package com.sabra.wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public class WithdrawalRequest {

    @NotNull(message = "Amount Is Required ")
    @DecimalMin(value = "0.01",message = "Amount must be at least 0.01")
    @Digits(integer = 10, fraction = 2 , message = "Amount must be at most 2 decimal places")
    private BigDecimal amount;


    public WithdrawalRequest() {
    }

    public WithdrawalRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
