package com.retailer.rewards.dto;

import java.time.LocalDate;
/**
 * Data Transfer Object used to
 * receive transaction response data
 * to API clients.
 *
 * This DTO is used for fetching customer transaction details.
 */
public class TransactionResponseDto {
    private Long id;
    private CustomerResponseDto customerResponseDto;
    private Double amount;
    private LocalDate transactionDate;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(Long id, CustomerResponseDto customerResponseDto, Double amount, LocalDate transactionDate) {
        this.id = id;
        this.customerResponseDto = customerResponseDto;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerResponseDto getCustomerResponseDto() {
        return customerResponseDto;
    }

    public void setCustomerResponseDto(CustomerResponseDto customerResponseDto) {
        this.customerResponseDto = customerResponseDto;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
