package com.sabra.wallet.repository;

import com.sabra.wallet.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Page<Transaction> findByWalletId(Long walletId, Pageable pageable);
}
