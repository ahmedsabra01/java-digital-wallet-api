package com.sabra.wallet.mapper;


import com.sabra.wallet.dto.response.DepositResponse;
import com.sabra.wallet.dto.response.TransactionResponse;
import com.sabra.wallet.dto.response.TransferResponse;
import com.sabra.wallet.dto.response.WithdrawalResponse;
import com.sabra.wallet.entity.Transaction;

import org.springframework.stereotype.Component;



@Component
public class TransactionMapper {

    public DepositResponse depositResponse(Transaction transaction){
        return new DepositResponse(transaction.getId(),
                transaction.getWallet().getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getCreatedAt());
    }

    public TransactionResponse transactionResponse(Transaction transaction){
        return new TransactionResponse(transaction.getId(),
                transaction.getWallet().getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getRelatedTransactionId(),
                transaction.getCreatedAt());
    }

    public WithdrawalResponse withdrawalResponse(Transaction transaction){
        return new WithdrawalResponse(transaction.getId(),
                transaction.getWallet().getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getCreatedAt());
    }

    public TransferResponse transferResponse(Transaction senderTransaction ,Transaction receiverTransaction ){
        return new TransferResponse(receiverTransaction.getWallet().getId(),
                senderTransaction.getWallet().getId(),
                senderTransaction.getAmount(),
                senderTransaction.getBalanceAfter(),
                receiverTransaction.getBalanceAfter(),
                receiverTransaction.getId(),
                senderTransaction.getId(),
                senderTransaction.getCreatedAt());

    }



}
