package com.sabra.wallet.controller;


import com.sabra.wallet.dto.request.DepositRequest;
import com.sabra.wallet.dto.request.WalletCreateRequest;
import com.sabra.wallet.dto.response.WalletResponse;
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
    @GetMapping("/customers/{customerID}/wallet")
    public WalletResponse getWalletByCustomerID(@PathVariable Long customerID){
        return walletService.getWalletByCustomerId(customerID);
    }
    @GetMapping("/wallets/{walletId}")
    public WalletResponse getWalletByWalletId(@PathVariable Long walletId){
        return walletService.getWalletByWalletId(walletId);
    }
    @PostMapping("/wallets/{walletId}/deposit")
    public WalletResponse deposit(@PathVariable Long walletId ,@RequestBody @Valid DepositRequest request){
        return walletService.deposit(walletId , request);
    }
}
