package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.CustomerCreateRequest;
import com.sabra.wallet.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
    CustomerResponse getCustomerById(Long id);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse updateCustomer(Long id , CustomerCreateRequest request);
    void deleteCustomer(Long id);
}
