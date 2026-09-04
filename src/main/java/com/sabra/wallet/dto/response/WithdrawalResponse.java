package com.sabra.wallet.dto.response;

import com.sabra.wallet.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawalResponse {
    private Long transactionId;
    private Long walletId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;


    public WithdrawalResponse() {
    }

    public WithdrawalResponse(Long transactionId, Long walletId, TransactionType transactionType, BigDecimal amount, BigDecimal balanceAfter, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }


    public Long getTransactionId() {
        return transactionId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
