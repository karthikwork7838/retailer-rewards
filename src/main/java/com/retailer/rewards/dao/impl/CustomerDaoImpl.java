package com.retailer.rewards.dao.impl;

import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dto.CustomerDto;
import com.retailer.rewards.entity.CustomerEntity;
import com.retailer.rewards.mapper.CustomerEntityMapper;
import com.retailer.rewards.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) implementation for customer-related database
 * operations.
 * 
 * This class implements the {@code CustomerDao} interface and serves as a
 * bridge between
 * the service layer and the repository layer. It encapsulates all database
 * query logic
 * for customer data and provides methods to retrieve customer information with
 * their
 * associated transactions.
 * 
 * <p>
 * <b>Responsibilities:</b>
 * <ul>
 * <li>Execute database queries through the {@code CustomerRepository}</li>
 * <li>Map database entities to Data Transfer Objects (DTOs) using
 * {@code CustomerEntityMapper}</li>
 * <li>Filter customer data based on cutoff dates to retrieve relevant
 * transactions</li>
 * <li>Handle empty result sets and optional values gracefully</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Design Pattern:</b> This class implements the Data Access Object pattern,
 * which provides
 * an abstraction between the business logic layer and the persistence layer,
 * making the
 * application more maintainable and testable.
 * </p>
 * 
 * <p>
 * <b>Dependencies Injected:</b>
 * <ul>
 * <li>{@code CustomerRepository} - Spring Data JPA repository for database
 * operations</li>
 * <li>{@code CustomerEntityMapper} - MapStruct mapper for entity-to-DTO
 * conversion</li>
 * </ul>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.dao.CustomerDao
 * @see com.retailer.rewards.repository.CustomerRepository
 * @see com.retailer.rewards.mapper.CustomerEntityMapper
 */
@Component
public class CustomerDaoImpl implements CustomerDao {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerEntityMapper customerEntityMapper;

    /**
     * Parameterized constructor that injects the required dependencies.
     * 
     * <p>
     * This constructor is invoked by the Spring container during bean
     * initialization to inject
     * the {@code CustomerRepository} and {@code CustomerEntityMapper} dependencies.
     * Both
     * dependencies are required for the DAO operations to function correctly.
     * </p>
     * 
     * @param customerRepository   the Spring Data JPA repository for executing
     *                             database queries.
     *                             Must not be null.
     * @param customerEntityMapper the MapStruct mapper for converting
     *                             {@code CustomerEntity}
     *                             objects to {@code CustomerDto} objects. Must not
     *                             be null.
     */
    public CustomerDaoImpl(CustomerRepository customerRepository, CustomerEntityMapper customerEntityMapper) {
        this.customerRepository = customerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    /**
     * Retrieves a list of all customers along with their transactions after a
     * specified cutoff date.
     * 
     * <p>
     * This method queries the database to fetch all customers and their associated
     * transactions.
     * Only transactions that occurred after the specified cutoff date are included
     * in the results.
     * The returned data is mapped from {@code CustomerEntity} to
     * {@code CustomerDto} objects
     * for transmission to the service layer.
     * </p>
     * 
     * <p>
     * <b>Process Flow:</b>
     * <ol>
     * <li>Initialize an empty list to store customer DTOs</li>
     * <li>Query the repository for all customers with transactions after the cutoff
     * date</li>
     * <li>If the result set is not empty, map each {@code CustomerEntity} to
     * {@code CustomerDto}</li>
     * <li>Return the mapped list, or an empty list if no customers found</li>
     * </ol>
     * </p>
     * 
     * @param cutoffDate the date threshold for filtering transactions. Only
     *                   transactions
     *                   with dates after this cutoff date will be included. Must
     *                   not be null.
     * 
     * @return a {@code List<CustomerDto>} containing all customers with their
     *         filtered transactions.
     *         Returns an empty list if no customers are found in the database.
     */
    @Override
    public List<CustomerDto> getAllCustomers(LocalDate cutoffDate) {
        List<CustomerDto> customerResponseDtoList = new ArrayList<>();
        List<CustomerEntity> customerEntityList = customerRepository.findAllCustomersWithTransactions(cutoffDate);
        if (!CollectionUtils.isEmpty(customerEntityList)) {
            customerResponseDtoList = customerEntityList.stream()
                    .map(customer -> customerEntityMapper.mapFrom(customer)).toList();
        }
        return customerResponseDtoList;
    }

    /**
     * Retrieves a specific customer by their unique customer ID along with their
     * transactions.
     * 
     * <p>
     * This method queries the database to fetch a specific customer identified by
     * their unique
     * customer ID, along with their associated transactions. Only transactions that
     * occurred
     * after the specified cutoff date are included in the results. The returned
     * data is mapped
     * from {@code CustomerEntity} to {@code CustomerDto}.
     * </p>
     * 
     * <p>
     * <b>Process Flow:</b>
     * <ol>
     * <li>Initialize an empty {@code CustomerDto}</li>
     * <li>Query the repository for the customer with the specified ID and
     * transactions after cutoff date</li>
     * <li>If the customer is found (Optional is present), map the
     * {@code CustomerEntity} to {@code CustomerDto}</li>
     * <li>Return the mapped DTO, or an empty DTO if customer not found</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Note:</b> If the customer is not found, an empty {@code CustomerDto} is
     * returned rather
     * than throwing an exception. The service layer is responsible for handling the
     * case where
     * a customer is not found and throwing appropriate exceptions (e.g.,
     * {@code CustomerNotFoundException}).
     * </p>
     * 
     * @param customerId the unique identifier of the customer to retrieve. Must be
     *                   a positive long value.
     * @param cutoffDate the date threshold for filtering transactions. Only
     *                   transactions
     *                   with dates after this cutoff date will be included. Must
     *                   not be null.
     * 
     * @return a {@code CustomerDto} containing the customer details and their
     *         filtered transactions.
     *         Returns an empty {@code CustomerDto} if no customer is found with the
     *         specified ID.
     */
    @Override
    public CustomerDto getCustomerById(Long customerId, LocalDate cutoffDate) {
        CustomerDto customerDto = new CustomerDto();
        Optional<CustomerEntity> customerEntity = customerRepository.findCustomerWithTransactions(customerId,
                cutoffDate);
        if (customerEntity.isPresent()) {
            customerDto = customerEntityMapper.mapFrom(customerEntity.get());
        }
        return customerDto;
    }
}