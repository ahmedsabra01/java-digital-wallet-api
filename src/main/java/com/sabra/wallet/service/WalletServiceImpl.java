package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.DepositRequest;
import com.sabra.wallet.dto.request.WalletCreateRequest;
import com.sabra.wallet.dto.request.WithdrawalRequest;
import com.sabra.wallet.dto.response.DepositResponse;
import com.sabra.wallet.dto.response.WalletResponse;
import com.sabra.wallet.dto.response.WithdrawalResponse;
import com.sabra.wallet.entity.Customer;
import com.sabra.wallet.entity.Transaction;
import com.sabra.wallet.entity.TransactionType;
import com.sabra.wallet.entity.Wallet;
import com.sabra.wallet.exception.CustomerNotFoundException;
import com.sabra.wallet.exception.InsufficientBalanceException;
import com.sabra.wallet.exception.WalletAlreadyExistsException;
import com.sabra.wallet.exception.WalletNotFoundException;
import com.sabra.wallet.mapper.TransactionMapper;
import com.sabra.wallet.mapper.WalletMapper;
import com.sabra.wallet.repository.CustomerRepository;
import com.sabra.wallet.repository.TransactionRepository;
import com.sabra.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService{
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public WalletServiceImpl(CustomerRepository customerRepository, WalletRepository walletRepository, WalletMapper walletMapper, TransactionRepository transactionRepository , TransactionMapper transactionMapper) {
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public WalletResponse createWallet(Long customerId, WalletCreateRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found With ID "+ customerId));
        if(walletRepository.existsByCustomerId(customerId)){
            throw new WalletAlreadyExistsException("Customer Already has A Wallet");
        }
        Wallet wallet = walletMapper.toEntity(request);
        wallet.setCustomer(customer);
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toResponse(savedWallet);

    }

    @Override
    public WalletResponse getWalletByCustomerId(Long customerId) {
        customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer Not Found With ID "+ customerId));
        Wallet wallet = walletRepository.findByCustomerId(customerId).orElseThrow(()-> new WalletNotFoundException("Wallet Not Found For Customer ID "+ customerId));
        return walletMapper.toResponse(wallet);
    }
    @Override
    public WalletResponse getWalletByWalletId(Long walletId){
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(()->new WalletNotFoundException("Wallet Not Found With ID "+walletId));
        return walletMapper.toResponse(wallet);

    }

    @Override
    @Transactional
    public DepositResponse deposit(Long walletId , DepositRequest request){
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(()->new WalletNotFoundException("Wallet Not Found With ID "+walletId));
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal newBalance = currentBalance.add(request.getAmount());

        wallet.setBalance(newBalance);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(request.getAmount());
        transaction.setBalanceAfter(newBalance);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        walletRepository.save(wallet);
        transactionRepository.save(transaction);

        return transactionMapper.depositResponse(transaction);
    }

    @Override
    @Transactional
    public WithdrawalResponse withdraw(Long walletId, WithdrawalRequest request) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(()-> new WalletNotFoundException("Wallet Not Found With ID "+ walletId));
        BigDecimal currentBalance = wallet.getBalance();
        BigDecimal amount = request.getAmount();
        if(currentBalance.compareTo(amount)<0){
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        BigDecimal newBalance = currentBalance.subtract(amount);
        wallet.setBalance(newBalance);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setBalanceAfter(newBalance);

        walletRepository.save(wallet);
        transactionRepository.save(transaction);



        return transactionMapper.withdrawalResponse(transaction);
    }

}
