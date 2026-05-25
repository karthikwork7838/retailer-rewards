package com.retailer.rewards.constants;
/**
 * Utility class containing application-wide
 * constant values used across the rewards
 * application.
 *
 * This class prevents hardcoded values and
 * improves maintainability.
 */
public class CommonConstants {
    public static final String API = "/api/v1";
    public static final String GET_ALL_CUSTOMERS = "/allCustomers";
    public static final String CUSTOMER_ID = "customers/{id}";
    public static final String CROSS_ORIGIN = "${cross.origin}";
    public static final String SAVE_CUSTOMER_DETAILS = "/saveCustomer";
    public static final String GET_ALL_REWARDS = "/allRewards";
    public static final String REWARD_BY_CUSTOMER_ID = "/rewards/{customerId}";
}
