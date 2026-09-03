package com.sabra.wallet.controller;

import com.sabra.wallet.dto.response.TransactionResponse;
import com.sabra.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{walletId}/transactions")
    public Page<TransactionResponse> getWalletTransactions(@PathVariable Long walletId, Pageable pageable){
        return transactionService.getWalletTransactions(walletId,pageable);
    }
}
