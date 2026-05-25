package com.retailer.rewards.controller;

import com.retailer.rewards.constants.CommonConstants;
import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author Karthik BK
 * This is a class(@link CustomerController).This controller class contains endpoint to fetch all customers,fetch individual
 * customer by customer id and also update and delete customer endpoints to perform customer detail update as well delete customer
 * by customer id
 */
@RestController
@RequestMapping(value = CommonConstants.API, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = CommonConstants.CROSS_ORIGIN)
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * This is a parameterized constructor and autowiring required properties
     * @param customerService
     */
    @Autowired
    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }

    /**
     *This method handles creation of customer based on the details in the request body
     * @param requestDto
     * @return ResponseEntity<CustomerResponseDto>
     */
    @PostMapping(value = CommonConstants.SAVE_CUSTOMER_DETAILS,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(requestDto));
    }

    /**
     * This method is the end point which returns the list of all customers available in customer table
     * @return List<CustomerResponseDto> List of customer objects
     */
    @GetMapping(value = CommonConstants.GET_ALL_CUSTOMERS,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerResponseDto> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    /**
     * This method returns the customer detail based on customer ID passed
     * @param id Customer ID unique for each customer
     * @return CustomerResponseDto Customer detail consisting of customer name, customer ID
     */
    @GetMapping(value = CommonConstants.CUSTOMER_ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponseDto getCustomerById(@PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    /**
     *This method is used to update the customer details
     * @param id
     * @param requestDto Request object
     * @returns CustomerResponseDto the updated customer detail
     */
    @PutMapping(value = CommonConstants.CUSTOMER_ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponseDto updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDto requestDto) {

        return customerService.updateCustomer(id, requestDto);
    }

    /**
     * This method is used to delete the customer object by customer ID
     * @param id Customer ID
     * @return
     */
    @DeleteMapping(value = CommonConstants.CUSTOMER_ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}