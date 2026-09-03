package com.sabra.wallet.controller;

import com.sabra.wallet.dto.request.CustomerCreateRequest;
import com.sabra.wallet.dto.response.CustomerResponse;
import com.sabra.wallet.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerCreateRequest request){
        return customerService.createCustomer(request);
    }
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PutMapping("/id")
    public CustomerResponse updateCustomer(@PathVariable Long id ,@RequestBody @Valid CustomerCreateRequest request){
        return customerService.updateCustomer(id , request);
    }

    @DeleteMapping("/id")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }
}
