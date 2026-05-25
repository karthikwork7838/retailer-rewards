package com.retailer.rewards.service;

import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);

    List<CustomerResponseDto> getAllCustomers();

    CustomerResponseDto getCustomerById(Long customerId);

    CustomerResponseDto updateCustomer(Long customerId, CustomerRequestDto requestDto);

    void deleteCustomer(Long customerId);
}