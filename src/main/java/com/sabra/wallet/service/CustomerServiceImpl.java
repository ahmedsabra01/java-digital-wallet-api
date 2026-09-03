package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.CustomerCreateRequest;
import com.sabra.wallet.dto.response.CustomerResponse;
import com.sabra.wallet.entity.Customer;
import com.sabra.wallet.exception.CustomerNotFoundException;
import com.sabra.wallet.mapper.CustomerMapper;
import com.sabra.wallet.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    
    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for(Customer c: customers){
            customerResponses.add(customerMapper.toResponse(c));
        }
        return customerResponses;
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id ,CustomerCreateRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer Not found with id "+id));
        customerRepository.save(customer);
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer Not found with id "+id));
        customerRepository.delete(customer);
        System.out.println("Deleted Customer With ID " + id );
    }
}
