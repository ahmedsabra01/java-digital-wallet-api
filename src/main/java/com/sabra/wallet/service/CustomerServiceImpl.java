package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.CustomerCreateRequest;
import com.sabra.wallet.dto.response.CustomerResponse;
import com.sabra.wallet.entity.Customer;
import com.sabra.wallet.exception.CustomerNotFoundException;
import com.sabra.wallet.mapper.CustomerMapper;
import com.sabra.wallet.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }


    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        Customer customer = customerMapper.toEntity(request);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer Not found with id "+id));
        return customerMapper.toResponse(customer);
    }
}
