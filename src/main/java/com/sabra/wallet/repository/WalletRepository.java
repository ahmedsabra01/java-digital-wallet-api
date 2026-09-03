package com.sabra.wallet.repository;

import com.sabra.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

    boolean existsByCustomerId(Long customerId);
    Optional<Wallet> findByCustomerId(Long customerId);
}
