package com.sabra.wallet.controller;


import com.sabra.wallet.dto.request.DepositRequest;
import com.sabra.wallet.dto.request.TransferRequest;
import com.sabra.wallet.dto.request.WalletCreateRequest;
import com.sabra.wallet.dto.request.WithdrawalRequest;
import com.sabra.wallet.dto.response.DepositResponse;
import com.sabra.wallet.dto.response.TransferResponse;
import com.sabra.wallet.dto.response.WalletResponse;
import com.sabra.wallet.dto.response.WithdrawalResponse;
import com.sabra.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @PostMapping("/customers/{customerId}/wallet")
    public WalletResponse createWallet(@PathVariable Long customerId ,@Valid @RequestBody WalletCreateRequest request){
        return walletService.createWallet(customerId , request);
    }
    @GetMapping("/customers/{customerId}/wallet")
    public WalletResponse getWalletByCustomerId(@PathVariable Long customerId){
        return walletService.getWalletByCustomerId(customerId);
    }
    @GetMapping("/wallets/{walletId}")
    public WalletResponse getWalletByWalletId(@PathVariable Long walletId){
        return walletService.getWalletByWalletId(walletId);
    }
    @PostMapping("/wallets/{walletId}/deposit")
    public DepositResponse deposit(@PathVariable Long walletId ,@RequestBody @Valid DepositRequest request){
        return walletService.deposit(walletId , request);
    }
    @PostMapping("/wallets/{walletID}/withdraw")
    public WithdrawalResponse withdraw(@PathVariable Long walletID, @Valid @RequestBody WithdrawalRequest request){
        return walletService.withdraw(walletID,request);
    }

    @PostMapping("/wallets/{walletId}/transfer")
    public TransferResponse transfer(@PathVariable Long walletId ,@Valid @RequestBody TransferRequest request){
        return walletService.transfer(walletId,request);
    }
}
