package com.retailer.rewards.mapper;

import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.entity.CustomerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Mapper class responsible for
 * converting customer entity
 * objects to DTOs and vice versa.
 * <p>
 * This class centralizes mapping
 * logic to improve maintainability
 * and avoid duplicate conversion
 * code.
 */
@Component
public class CustomerEntityMapper {
    /**
     * Converts customer request DTO
     * into customer entity.
     * <p>
     * Used while creating or updating
     * customer records.
     *
     * @param @CustomerRequestDto request DTO
     * @return customer entity
     */
    public CustomerEntity mapTo(CustomerRequestDto customerRequest) {
        CustomerEntity customerEntity = new CustomerEntity();
        if (!ObjectUtils.isEmpty(customerRequest)) {
            customerEntity.setName(customerRequest.getName());
        }
        return customerEntity;
    }

    /**
     * Converts customer entity
     * into customer response DTO.
     * <p>
     * Used for sending customer
     * details in API response.
     *
     * @param customerEntity customer entity
     * @return customer response DTO
     */
    public CustomerResponseDto mapFrom(CustomerEntity customerEntity) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        if (!ObjectUtils.isEmpty(customerEntity)) {
            customerResponseDto.setId(customerEntity.getId());
            customerResponseDto.setName(customerEntity.getName());
        }
        return customerResponseDto;
    }
}
