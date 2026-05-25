package com.retailer.rewards.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object used to
 * receive customer request data
 * from API clients.
 *
 * This DTO is used for creating
 * and updating customer details.
 */
public class CustomerRequestDto {

    @NotBlank(message = "Customer name is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}