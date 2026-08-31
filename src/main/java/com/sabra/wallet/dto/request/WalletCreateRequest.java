package com.sabra.wallet.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class WalletCreateRequest {

    @NotBlank(message = "Currency is Required")
    private String currency;


    public WalletCreateRequest() {
    }

    public WalletCreateRequest( String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
