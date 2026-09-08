package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.DepositRequest;
import com.sabra.wallet.dto.request.TransferRequest;
import com.sabra.wallet.dto.request.WalletCreateRequest;
import com.sabra.wallet.dto.request.WithdrawalRequest;
import com.sabra.wallet.dto.response.DepositResponse;
import com.sabra.wallet.dto.response.TransferResponse;
import com.sabra.wallet.dto.response.WalletResponse;
import com.sabra.wallet.dto.response.WithdrawalResponse;

public interface WalletService {

    WalletResponse createWallet(Long customerId, WalletCreateRequest request);
    WalletResponse getWalletByCustomerId(Long customerId);
    WalletResponse getWalletByWalletId(Long walletId);
    DepositResponse deposit(Long walletId, DepositRequest request);
    WithdrawalResponse withdraw(Long walletId, WithdrawalRequest request);
    TransferResponse transfer(Long senderWalletId , TransferRequest request);
}
