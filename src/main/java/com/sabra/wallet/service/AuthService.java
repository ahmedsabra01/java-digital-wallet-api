package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.RegistrationRequest;

public interface AuthService {
    void register(RegistrationRequest request);

}
