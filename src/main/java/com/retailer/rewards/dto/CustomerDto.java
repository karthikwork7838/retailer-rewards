package com.retailer.rewards.dto;

import java.util.List;
/**
 * Data Transfer Object class for Customer details with list of transactions performed by the customer
 */
public class CustomerDto {
    private Long customerId;
    private String customerName;
    private List<TransactionDto> transactionList;

    public CustomerDto() {
    }

    public CustomerDto(Long customerId, String customerName, List<TransactionDto> transactionList) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.transactionList = transactionList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomername() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<TransactionDto> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionDto> transactionList) {
        this.transactionList = transactionList;
    }

}
