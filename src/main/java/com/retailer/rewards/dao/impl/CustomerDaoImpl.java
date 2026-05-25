package com.retailer.rewards.dao.impl;

import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.entity.CustomerEntity;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.mapper.CustomerEntityMapper;
import com.retailer.rewards.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * DAO implementation for
 * customer-related database
 * operations.
 *
 * This class interacts with
 * the repository layer to
 * perform CRUD operations
 * on customer data.
 */
@Component
public class CustomerDaoImpl implements CustomerDao {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    public CustomerDaoImpl(CustomerRepository customerRepository, CustomerEntityMapper customerEntityMapper) {
        this.customerRepository = customerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }
    /**
     * Saves customer information
     * into database.
     *
     * Used for both create
     * and update operations.
     *
     * @param @customerEntity
     *         customer entity
     *
     * @return saved customer entity
     */
    @Override
    public CustomerResponseDto saveCustomer(CustomerRequestDto customerRequest) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        CustomerEntity customerEntity = customerEntityMapper.mapTo(customerRequest);
        if (!ObjectUtils.isEmpty(customerEntity)) {
            customerEntity = customerRepository.save(customerEntity);
            customerResponseDto = customerEntityMapper.mapFrom(customerEntity);
        }
        return customerResponseDto;
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        List<CustomerEntity> customerEntityList = customerRepository.findAll();
        if (!CollectionUtils.isEmpty(customerEntityList)) {
            customerResponseDtoList = customerEntityList.stream().map(customer -> customerEntityMapper.mapFrom(customer)).toList();
        }
        return customerResponseDtoList;
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        if (customerEntity.isPresent()) {
            customerResponseDto = customerEntityMapper.mapFrom(customerEntity.get());
        }
        return customerResponseDto;
    }

    @Override
    public void deleteCustomer(Long customerID) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerID);
        if (customerEntity == null || customerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerID);
        }
        customerRepository.delete(customerEntity.get());
    }

    /**
     * @param customerId
     * @return
     */
    @Override
    public CustomerResponseDto updateCustomerById(Long customerId,CustomerRequestDto customerRequestDto) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if(customerEntityOptional.isPresent()){
         CustomerEntity customerEntity =  customerEntityOptional.get();
            customerEntity.setName(customerRequestDto.getName());
            customerEntity = customerRepository.save(customerEntity);
            customerResponseDto = customerEntityMapper.mapFrom(customerEntity);
        }else{
                throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        return customerResponseDto;
    }
}