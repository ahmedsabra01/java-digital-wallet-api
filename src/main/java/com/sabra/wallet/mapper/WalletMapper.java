package com.sabra.wallet.mapper;

import com.sabra.wallet.dto.request.WalletCreateRequest;
import com.sabra.wallet.dto.response.WalletResponse;
import com.sabra.wallet.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {
    public Wallet toEntity(WalletCreateRequest request){
        Wallet wallet = new Wallet();
        wallet.setCurrency(request.getCurrency());
        return wallet;
    }

    public WalletResponse toResponse(Wallet wallet){
        return new WalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getCurrency(),
                wallet.getCreatedAt()
        );
    }


}
