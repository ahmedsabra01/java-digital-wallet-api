package com.sabra.wallet.dto.response;

import java.time.LocalDateTime;

public class CustomerResponse {
    private Long id;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;


    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String fullName, String email, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
