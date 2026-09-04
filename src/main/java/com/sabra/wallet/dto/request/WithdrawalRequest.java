package com.sabra.wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public class WithdrawalRequest {

    @NotNull(message = "Amount Is Required ")
    @DecimalMin(value = "0.01",message = "Amount Must Be Greater Than Or Equal Zero")
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
