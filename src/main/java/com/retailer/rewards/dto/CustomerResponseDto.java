package com.retailer.rewards.dto;
/**
 * Data Transfer Object used to
 * send back customer response data
 * to API clients.
 *
 * This DTO is used for creating
 * and updating customer details.
 */
public class CustomerResponseDto {

    private Long id;
    private String name;

    public CustomerResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomerResponseDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}