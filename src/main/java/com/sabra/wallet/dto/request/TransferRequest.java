package com.sabra.wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {
    @NotNull(message = "You Should Enter Receiver ID")
    private Long receiverWalletId;
    @NotNull(message = "Amount is Required")
    @DecimalMin(value = "0.01" , message = "Amount Must be Greater Than Zero")
    private BigDecimal amount;

    public TransferRequest() {
    }

    public TransferRequest(Long receiverWalletId, BigDecimal amount) {
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
    }

    public Long getReceiverWalletId() {
        return receiverWalletId;
    }

    public void setReceiverWalletId(Long receiverWalletId) {
        this.receiverWalletId = receiverWalletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
