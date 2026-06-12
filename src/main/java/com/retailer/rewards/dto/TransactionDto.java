package com.retailer.rewards.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object used to
 * receive transaction response data
 * to API clients.
 *
 * This DTO is used for fetching customer transaction details.
 */
public class TransactionDto {
    private Long transactionId;
    private Long customerId;
    private BigDecimal transactionAmount;
    private LocalDate transactionDate;

    public TransactionDto() {
    }

    public TransactionDto(Long transactionId, Long customerId, BigDecimal transactionAmount,
            LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }


    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionResponseDto [transactionId=" + transactionId + ", customerId=" + customerId
                + ", transactionAmount=" + transactionAmount + ", transactionDate=" + transactionDate + "]";
    }

}
