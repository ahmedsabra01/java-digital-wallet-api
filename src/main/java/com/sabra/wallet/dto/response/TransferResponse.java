package com.sabra.wallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponse {
    private Long receiverWalletId;
    private Long senderWalletId;
    private BigDecimal amount;
    private BigDecimal senderBalanceAfter;
    private BigDecimal receiverBalanceAfter;
    private Long receiverTransactionId;
    private Long senderTransactionId;
    private LocalDateTime createdAt;


    public TransferResponse() {
    }


    public TransferResponse(Long receiverWalletId, Long senderWalletId, BigDecimal amount, BigDecimal senderBalanceAfter, BigDecimal receiverBalanceAfter, Long receiverTransactionId, Long senderTransactionId, LocalDateTime createdAt) {
        this.receiverWalletId = receiverWalletId;
        this.senderWalletId = senderWalletId;
        this.amount = amount;
        this.senderBalanceAfter = senderBalanceAfter;
        this.receiverBalanceAfter = receiverBalanceAfter;
        this.receiverTransactionId = receiverTransactionId;
        this.senderTransactionId = senderTransactionId;
        this.createdAt = createdAt;
    }

    public Long getReceiverWalletId() {
        return receiverWalletId;
    }

    public Long getSenderWalletId() {
        return senderWalletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getSenderBalanceAfter() {
        return senderBalanceAfter;
    }

    public BigDecimal getReceiverBalanceAfter() {
        return receiverBalanceAfter;
    }

    public Long getReceiverTransactionId() {
        return receiverTransactionId;
    }

    public Long getSenderTransactionId() {
        return senderTransactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
