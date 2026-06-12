package com.retailer.rewards.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * This is a Response Model class to provide the API response back to client. This contains customer details
 * such as ID, Name, Monthly Reward List and total points
 */
public class CustomerDataResponse {
    private Long customerId;
    private String customername;
    private List<MonthlyReward> monthlyRewards;
    private BigDecimal totalPoints;

    public CustomerDataResponse() {
    }

    public CustomerDataResponse(Long customerId, String customername, List<MonthlyReward> monthlyRewards,
            BigDecimal totalPoints) {
        this.customerId = customerId;
        this.customername = customername;
        this.monthlyRewards = monthlyRewards;
        this.totalPoints = totalPoints;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public List<MonthlyReward> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(List<MonthlyReward> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    public BigDecimal getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(BigDecimal totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "CustomerDataResponse [customerId=" + customerId + ", customername=" + customername + ", monthlyRewards="
                + monthlyRewards + ", totalPoints=" + totalPoints + "]";
    }

}
