package com.retailer.rewards.controller;

import com.retailer.rewards.constants.CommonConstants;
import com.retailer.rewards.dto.CustomerResponse;
import com.retailer.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing customer-related API endpoints.
 * 
 * This controller provides RESTful endpoints for customer operations including
 * retrieving all customers
 * and fetching individual customer details by customer ID. All endpoints return
 * JSON responses with
 * appropriate HTTP status codes.
 * 
 * <p>
 * <b>API Base Path:</b> {@code /api/v1}
 * </p>
 * 
 * <p>
 * <b>Endpoints:</b>
 * <ul>
 * <li>GET {@code /api/v1/allCustomers} - Retrieve all customers</li>
 * <li>GET {@code /api/v1/customers/{customerId}} - Retrieve a specific customer
 * by ID</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>CORS Configuration:</b> Cross-Origin Resource Sharing is enabled globally
 * via the
 * {@code CorsConfig} configuration class. Allowed origins are configured via
 * the
 * {@code cross.origin} application property, enabling frontend applications
 * from specified
 * origins to access these endpoints.
 * </p>
 * 
 * <p>
 * <b>Exception Handling:</b> Any exceptions thrown by the service layer are
 * handled by the
 * global exception handler ({@code GlobalExceptionHandler}), which converts
 * them to appropriate
 * HTTP error responses.
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.service.CustomerService
 * @see com.retailer.rewards.dto.CustomerResponse
 */
@RestController
@RequestMapping(value = CommonConstants.API, produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * Parameterized constructor that injects the CustomerService dependency.
     * 
     * <p>
     * Spring's {@code @Autowired} annotation enables automatic dependency injection
     * of the
     * {@code CustomerService} bean. This constructor is invoked by the Spring
     * container during
     * bean initialization to satisfy the required dependencies for this controller.
     * </p>
     * 
     * @param customerService the service layer component responsible for customer
     *                        business logic
     *                        and data operations. Must not be null.
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves a list of all customers from the database.
     * 
     * <p>
     * <b>HTTP Method:</b> GET
     * </p>
     * 
     * <p>
     * <b>Endpoint:</b> {@code /api/v1/allCustomers}
     * </p>
     * 
     * <p>
     * <b>Response:</b>
     * <ul>
     * <li><b>Status:</b> 200 OK</li>
     * <li><b>Content-Type:</b> application/json</li>
     * <li><b>Body:</b> {@code CustomerResponse} object containing list of all
     * customers</li>
     * </ul>
     * </p>
     * 
     * @return {@code ResponseEntity<CustomerResponse>} containing the list of all
     *         customers
     *         with HTTP 200 status code and success message
     * 
     * @throws Exception if an error occurs during data retrieval. Handled by global
     *                   exception handler.
     */
    @GetMapping(value = CommonConstants.GET_ALL_CUSTOMERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getAllCustomers() {
        CustomerResponse customerResponse = new CustomerResponse(customerService.getAllCustomers());
        return customerResponse.build(CommonConstants.CUSTOMER_LOAD, CommonConstants.STATUS_CODE_200, customerResponse);
    }

    /**
     * Retrieves a specific customer by their unique customer ID.
     * 
     * <p>
     * <b>HTTP Method:</b> GET
     * </p>
     * 
     * <p>
     * <b>Endpoint:</b> {@code /api/v1/customers/{customerId}}
     * </p>
     * 
     * <p>
     * <b>Path Variables:</b>
     * <ul>
     * <li>{@code customerId} - Unique identifier of the customer (Long,
     * required)</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Response:</b>
     * <ul>
     * <li><b>Status:</b> 200 OK on success</li>
     * <li><b>Content-Type:</b> application/json</li>
     * <li><b>Body:</b> {@code CustomerResponse} object containing customer
     * details</li>
     * </ul>
     * </p>
     * 
     * @param customerId the unique identifier of the customer to retrieve. Must be
     *                   a valid, positive long value.
     * 
     * @return {@code ResponseEntity<CustomerResponse>} containing the customer
     *         details
     *         with HTTP 200 status code and success message
     * 
     * @throws CustomerNotFoundException if no customer is found with the specified
     *                                   customerId.
     *                                   Handled by global exception handler and
     *                                   returns HTTP 404.
     */
    @GetMapping(value = CommonConstants.CUSTOMER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {
        CustomerResponse customerResponse = new CustomerResponse(customerService.getCustomerById(customerId));
        return customerResponse.build(CommonConstants.CUSTOMER_LOAD, CommonConstants.STATUS_CODE_200, customerResponse);
    }
}