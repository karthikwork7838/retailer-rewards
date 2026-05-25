package com.retailer.rewards.dto;

import java.util.Map;
/**
 * Data Transfer Object used to
 * receive customer reward response data
 * from API clients.
 *
 * This DTO is used for fetching
 * and customer reward details.
 */
public class RewardResponseDto {

    private final Long customerId;
    private final String customerName;
    private final Map<String, Integer> monthlyRewards;
    private final Integer totalRewards;

    public RewardResponseDto(Long customerId, String customerName, Map<String, Integer> monthlyRewards, Integer totalRewards) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyRewards = monthlyRewards;
        this.totalRewards = totalRewards;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Map<String, Integer> getMonthlyRewards() {
        return monthlyRewards;
    }

    public Integer getTotalRewards() {
        return totalRewards;
    }
}