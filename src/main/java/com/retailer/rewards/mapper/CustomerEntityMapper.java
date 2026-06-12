package com.retailer.rewards.mapper;

import com.retailer.rewards.dto.CustomerDto;
import com.retailer.rewards.dto.TransactionDto;
import com.retailer.rewards.entity.CustomerEntity;
import com.retailer.rewards.entity.TransactionEntity;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Mapper component responsible for converting between entity and Data Transfer
 * Object (DTO) layers.
 * 
 * This class centralizes all entity-to-DTO conversion logic for customer and
 * transaction data,
 * following the Single Responsibility Principle and improving code
 * maintainability. By consolidating
 * mapping logic in one place, it avoids duplication and makes changes to
 * transformation rules easier
 * to implement across the application.
 * 
 * <p>
 * <b>Conversion Patterns:</b>
 * <ul>
 * <li>Converts {@code CustomerEntity} objects to {@code CustomerDto} with
 * nested transaction conversions</li>
 * <li>Converts {@code TransactionEntity} objects to {@code TransactionDto}</li>
 * <li>Handles null and empty values gracefully using {@code ObjectUtils}</li>
 * <li>Supports nested entity-to-DTO conversions for related objects</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Data Flow:</b>
 * <ol>
 * <li>DAO layer retrieves entities from the database</li>
 * <li>This mapper converts entities to DTOs</li>
 * <li>Service layer uses DTOs to return data to the controller layer</li>
 * <li>Controllers return DTOs in JSON format to API consumers</li>
 * </ol>
 * </p>
 * 
 * <p>
 * <b>Benefits:</b>
 * <ul>
 * <li><b>Separation of Concerns:</b> Entities represent database structure,
 * DTOs represent API contracts</li>
 * <li><b>Flexibility:</b> Allows independent evolution of entity and API
 * response structures</li>
 * <li><b>Security:</b> DTOs can exclude sensitive fields that shouldn't be
 * exposed to clients</li>
 * <li><b>Performance:</b> DTOs can include only the data needed for specific
 * API responses</li>
 * </ul>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.entity.CustomerEntity
 * @see com.retailer.rewards.entity.TransactionEntity
 * @see com.retailer.rewards.dto.CustomerDto
 * @see com.retailer.rewards.dto.TransactionDto
 */
@Component
public class CustomerEntityMapper {

    /**
     * Converts a {@code CustomerEntity} to a {@code CustomerDto} with nested
     * transaction conversions.
     * 
     * <p>
     * This method performs the following transformations:
     * <ol>
     * <li>Maps customer ID from the entity to the DTO</li>
     * <li>Maps customer name from the entity to the DTO</li>
     * <li>Converts all associated {@code TransactionEntity} objects to
     * {@code TransactionDto} objects</li>
     * <li>Handles null and empty checks to prevent NullPointerException</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Null Safety:</b>
     * The method uses {@code ObjectUtils.isEmpty()} to check for null and empty
     * values before
     * accessing entity properties. If the customer entity is null/empty, a default
     * empty DTO is returned.
     * If transactions are null/empty, the DTO is returned with an empty or null
     * transaction list.
     * </p>
     * 
     * <p>
     * <b>Nested Mapping:</b>
     * All transactions associated with the customer are mapped to DTOs using the
     * static
     * {@code mapFrom(TransactionEntity)} method. This ensures complete and
     * consistent conversion
     * of nested objects.
     * </p>
     * 
     * <p>
     * <b>Usage Example:</b>
     * 
     * <pre>
     * {@code
     * CustomerEntity entity = customerRepository.findById(1L).orElse(null);
     * CustomerDto dto = mapper.mapFrom(entity);
     * // dto now contains customer details and all their transactions
     * }
     * </pre>
     * </p>
     * 
     * @param customerEntity the customer entity object to convert. May be null or
     *                       empty.
     * 
     * @return a {@code CustomerDto} containing customer details and a list of
     *         transaction DTOs.
     *         Returns an empty DTO if the input entity is null/empty. Never returns
     *         null.
     */
    public CustomerDto mapFrom(CustomerEntity customerEntity) {
        CustomerDto customerResponse = new CustomerDto();
        if (!ObjectUtils.isEmpty(customerEntity)) {
            customerResponse.setCustomerId(customerEntity.getCustomerId());
            customerResponse.setCustomerName(customerEntity.getCustomerName());
        }
        if (!ObjectUtils.isEmpty(customerEntity.getTransactions())) {
            customerResponse.setTransactionList(
                    customerEntity.getTransactions().stream().map(CustomerEntityMapper::mapFrom).toList());
        }
        return customerResponse;
    }

    /**
     * Converts a {@code TransactionEntity} to a {@code TransactionDto}.
     * 
     * <p>
     * This static method performs the following transformations:
     * <ol>
     * <li>Extracts the customer ID from the associated customer entity</li>
     * <li>Maps transaction amount from the entity to the DTO</li>
     * <li>Maps transaction date from the entity to the DTO</li>
     * <li>Maps transaction ID from the entity to the DTO</li>
     * <li>Handles null customer entity reference gracefully</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Field Mapping Details:</b>
     * <ul>
     * <li>{@code transactionEntity.getCustomerEntity().getCustomerId()} →
     * {@code transactionDto.setCustomerId()}</li>
     * <li>{@code transactionEntity.getAmount()} →
     * {@code transactionDto.setTransactionAmount()}</li>
     * <li>{@code transactionEntity.getTransactionDate()} →
     * {@code transactionDto.setTransactionDate()}</li>
     * <li>{@code transactionEntity.getTransactionId()} →
     * {@code transactionDto.setTransactionId()}</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Null Safety:</b>
     * The method uses {@code ObjectUtils.isEmpty()} to check if the associated
     * customer entity is
     * null before attempting to access its customer ID. If the customer is null,
     * the customer ID
     * in the DTO will remain unset.
     * </p>
     * 
     * <p>
     * <b>Usage as Static Method:</b>
     * This method is declared as static to allow it to be used as a method
     * reference in
     * stream operations and for utility-style access without requiring an instance
     * of the mapper.
     * </p>
     * 
     * <p>
     * <b>Usage Examples:</b>
     * 
     * <pre>
     * {@code
     * // Direct method call
     * TransactionDto dto = CustomerEntityMapper.mapFrom(transactionEntity);
     * 
     * // As method reference in stream
     * List<TransactionDto> dtos = transactionEntities.stream()
     *         .map(CustomerEntityMapper::mapFrom)
     *         .toList();
     * }
     * </pre>
     * </p>
     * 
     * @param transactionEntity the transaction entity object to convert. Must not
     *                          be null.
     * 
     * @return a {@code TransactionDto} containing transaction details including
     *         customer ID,
     *         amount, date, and transaction ID. Never returns null.
     */
    public static TransactionDto mapFrom(TransactionEntity transactionEntity) {
        TransactionDto transactionDto = new TransactionDto();
        if (!ObjectUtils.isEmpty(transactionEntity.getCustomerEntity())) {
            transactionDto.setCustomerId(transactionEntity.getCustomerEntity().getCustomerId());
        }
        transactionDto.setTransactionAmount(transactionEntity.getAmount());
        transactionDto.setTransactionDate(transactionEntity.getTransactionDate());
        transactionDto.setTransactionId(transactionEntity.getTransactionId());
        return transactionDto;
    }
}
