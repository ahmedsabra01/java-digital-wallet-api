package com.sabra.wallet.service;


import com.sabra.wallet.dto.response.TransactionResponse;
import com.sabra.wallet.entity.Transaction;
import com.sabra.wallet.entity.Wallet;
import com.sabra.wallet.exception.WalletNotFoundException;
import com.sabra.wallet.mapper.TransactionMapper;
import com.sabra.wallet.repository.TransactionRepository;
import com.sabra.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WalletRepository walletRepository;
    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletRepository walletRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Page<TransactionResponse> getWalletTransactions(Long walletId, Pageable pageable) {
        walletRepository.findById(walletId).orElseThrow(()->new WalletNotFoundException("Wallet NotFound With ID "+ walletId));
        Page<Transaction> transactions = transactionRepository.findByWalletId(walletId , pageable);
        return transactions.map(transactionMapper::transactionResponse);
    }
}
