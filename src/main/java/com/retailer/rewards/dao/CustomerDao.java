package com.retailer.rewards.dao;

import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.entity.CustomerEntity;

import java.util.List;

public interface CustomerDao {

    CustomerResponseDto saveCustomer(CustomerRequestDto customerEntity);

    List<CustomerResponseDto> getAllCustomers();

    CustomerResponseDto getCustomerById(Long id);

    void deleteCustomer(Long customerId);

    CustomerResponseDto updateCustomerById(Long customerId,CustomerRequestDto customerRequestDto);
}