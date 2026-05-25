package com.retailer.rewards.service.impl;

import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for
 * customer-related business
 * operations.
 * <p>
 * This class handles customer
 * creation, retrieval, update
 * and deletion functionality.
 * <p>
 * DTO to entity conversion is
 * delegated to mapper classes
 * to maintain separation of
 * concerns.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Creates a new customer.
     * <p>
     * Converts request DTO
     * into entity and persists
     * customer information.
     *
     * @param requestDto customer request DTO
     * @return created customer
     * response DTO
     */
    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        CustomerResponseDto savedCustomer = customerDao.saveCustomer(requestDto);
        return savedCustomer;
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        customerResponseDtos = customerDao.getAllCustomers();
        return customerResponseDtos;
    }

    /**
     * Retrieves all customers.
     *
     * @return list of customer
     * response DTOs
     */
    @Override
    public CustomerResponseDto getCustomerById(Long customerId) {

        CustomerResponseDto customerResponseDto = customerDao.getCustomerById(customerId);

        if (ObjectUtils.isEmpty(customerResponseDto)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        return customerResponseDto;
    }

    /**
     * Retrieves customer by id.
     * <p>
     * Throws exception if
     * customer does not exist.
     *
     * @param customerId unique customer id
     * @return customer response DTO
     */
    @Override
    public CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto requestDto) {
        CustomerResponseDto customerResponse = customerDao.updateCustomerById(customerId, requestDto);
        return customerResponse;
    }

    /**
     * Updates customer details.
     * <p>
     * Throws exception if
     * customer does not exist.
     *
     * @param customerId  unique customer id
     * @param @requestDto updated customer data
     * @return updated customer
     * response DTO
     */
    @Override
    public void deleteCustomer(Long customerId) {
        customerDao.deleteCustomer(customerId);
    }
}