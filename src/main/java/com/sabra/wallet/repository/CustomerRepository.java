package com.sabra.wallet.repository;

import com.sabra.wallet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);
}
