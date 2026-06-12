package com.retailer.rewards.dao;

import com.retailer.rewards.dto.CustomerDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object (DAO) interface for customer-related database operations.
 * 
 * This interface defines the contract for accessing customer data from the
 * database.
 * It abstracts the database operations from the service layer, allowing for
 * loose coupling
 * between layers and facilitating easier testing through dependency injection
 * and mocking.
 * 
 * <p>
 * <b>Responsibilities:</b>
 * <ul>
 * <li>Define methods for querying customer data</li>
 * <li>Fetch all customers with their transaction history</li>
 * <li>Fetch individual customers by ID with their transaction history</li>
 * <li>Support filtering transactions based on cutoff dates</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Design Pattern:</b> This interface follows the Data Access Object (DAO)
 * pattern,
 * which provides a cleaner separation of concerns between the business logic
 * and persistence layers.
 * Implementation classes ({@code CustomerDaoImpl}) handle the actual database
 * queries and entity
 * mapping operations.
 * </p>
 * 
 * <p>
 * <b>Date Filtering:</b> All methods accept a {@code LocalDate cutoffDate}
 * parameter to filter
 * transactions. This allows retrieval of customer data with transactions that
 * occurred after
 * the specified cutoff date, enabling flexible reporting and data analysis.
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.dao.impl.CustomerDaoImpl
 * @see com.retailer.rewards.dto.CustomerDto
 */
public interface CustomerDao {

    /**
     * Retrieves a list of all customers along with their transactions after a
     * specified cutoff date.
     * 
     * <p>
     * This method queries the database to fetch all customers and their associated
     * transactions.
     * Only transactions that occurred after the specified cutoff date are included
     * in the results.
     * The data is returned as Data Transfer Objects (DTOs) for transport to the
     * service layer.
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
    List<CustomerDto> getAllCustomers(LocalDate cutoffDate);

    /**
     * Retrieves a specific customer by their unique customer ID along with their
     * transactions.
     * 
     * <p>
     * This method queries the database to fetch a specific customer identified by
     * their unique
     * customer ID, along with their associated transactions. Only transactions that
     * occurred after
     * the specified cutoff date are included in the results. The data is returned
     * as a Data
     * Transfer Object (DTO) for transport to the service layer.
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
     *         Returns an empty or null DTO if no customer is found with the
     *         specified ID.
     */
    CustomerDto getCustomerById(Long customerId, LocalDate cutoffDate);
}