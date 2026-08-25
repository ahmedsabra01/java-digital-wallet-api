package com.sabra.wallet.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "balance_after")
    private BigDecimal balanceAfter;
    @Column(name = "related_transaction_id")
    private Long relatedTransactionId;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;



    public Transaction() {
    }

    public Transaction(Long id, TransactionType transactionType, BigDecimal balanceAfter, BigDecimal amount, Long relatedTransactionId) {
        this.id = id;
        this.transactionType = transactionType;
        this.balanceAfter = balanceAfter;
        this.amount = amount;
        this.relatedTransactionId = relatedTransactionId;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getRelatedTransactionId() {
        return relatedTransactionId;
    }

    public void setRelatedTransactionId(Long relatedTransactionId) {
        this.relatedTransactionId = relatedTransactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                ", relatedTransactionId=" + relatedTransactionId +
                ", createdAt=" + createdAt +
                '}';
    }
}
