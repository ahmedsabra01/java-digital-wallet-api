package com.sabra.wallet.service;

import com.sabra.wallet.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface TransactionService {
    Page<TransactionResponse> getWalletTransactions(Long walletId , Pageable pageable);
}
