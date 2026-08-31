package com.sabra.wallet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerCreateRequest {
    @NotBlank(message = "Full Name is Required")
    private String fullName;
    @NotBlank(message = "Email is Required")
    @Email(message = "Email must be Valid")
    private String email;


    public CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
