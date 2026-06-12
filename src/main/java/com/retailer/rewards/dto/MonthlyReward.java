package com.retailer.rewards.dto;

import java.math.BigDecimal;
/**
 * Business model class for Monthly Reward
 */
public class MonthlyReward {
    private String month;
    private BigDecimal points;

    public MonthlyReward() {
    }

    public MonthlyReward(String month, BigDecimal points) {
        this.month = month;
        this.points = points;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "MonthlyReward [month=" + month + ", points=" + points + "]";
    }

}
