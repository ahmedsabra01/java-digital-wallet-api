package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.CustomerCreateRequest;
import com.sabra.wallet.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
    CustomerResponse getCustomerById(Long id);
}
