package com.retailer.rewards.constants;

/**
 * Utility class containing application-wide constant values used across the
 * rewards application.
 * 
 * This class serves as a centralized repository for all static constant values,
 * preventing
 * hardcoded values throughout the codebase and improving maintainability. All
 * constants are
 * final and static, ensuring they cannot be modified at runtime.
 * 
 * <p>
 * Constant Categories:
 * <ul>
 * <li><b>API Paths:</b> Base and endpoint paths for REST API routes</li>
 * <li><b>Configuration:</b> CORS and security-related configurations</li>
 * <li><b>Messages:</b> Response messages for API operations</li>
 * <li><b>HTTP Status:</b> HTTP status codes for responses</li>
 * </ul>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 */
public class CommonConstants {

    /**
     * Base API path for all REST endpoints.
     * Used as the root path for versioned API endpoints.
     */
    public static final String API = "/api/v1";

    /**
     * Endpoint path to retrieve all customers.
     * Appended to API base path to form complete endpoint:
     * {@code /api/v1/allCustomers}
     */
    public static final String GET_ALL_CUSTOMERS = "/allCustomers";

    /**
     * Endpoint path to retrieve a specific customer by ID.
     * Contains path variable {customerId} which is replaced at runtime with actual
     * customer ID.
     * Complete endpoint: {@code /api/v1/customers/{customerId}}
     */
    public static final String CUSTOMER_ID = "customers/{customerId}";

    /**
     * Success message displayed when a customer is successfully loaded.
     * Used in API response messages to indicate successful data retrieval.
     */
    public static final String CUSTOMER_LOAD = "Customer loaded successfully";

    /**
     * HTTP status code for successful request (OK).
     * Standard HTTP 200 response code indicating the request was successful.
     */
    public static final Integer STATUS_CODE_200 = 200;
}
