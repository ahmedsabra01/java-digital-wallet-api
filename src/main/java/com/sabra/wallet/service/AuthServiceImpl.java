package com.sabra.wallet.service;

import com.sabra.wallet.dto.request.RegistrationRequest;
import com.sabra.wallet.entity.Customer;
import com.sabra.wallet.entity.User;
import com.sabra.wallet.exception.EmailAlreadyExistsException;
import com.sabra.wallet.exception.UsernameAlreadyExistsException;
import com.sabra.wallet.repository.CustomerRepository;
import com.sabra.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthServiceImpl(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UsernameAlreadyExistsException("Username Already exists");
        }
        if(customerRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Customer customer = new Customer();
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());

        customerRepository.save(customer);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole("USER");
        user.setCustomer(customer);

        userRepository.save(user);

    }
}
