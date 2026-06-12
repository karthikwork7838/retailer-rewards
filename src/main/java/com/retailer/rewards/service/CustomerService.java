package com.retailer.rewards.service;

import com.retailer.rewards.dto.CustomerDataResponse;
import java.util.List;

/**
 * Service interface defining the contract for customer-related business
 * operations.
 * 
 * This interface defines methods for retrieving customer data and calculating
 * their reward
 * information. Implementation classes are responsible for coordinating between
 * the data access
 * layer (DAO) and the presentation layer (controllers), ensuring consistent
 * business logic
 * across the rewards application.
 * 
 * <p>
 * <b>Responsibilities:</b>
 * <ul>
 * <li>Define methods for customer data retrieval operations</li>
 * <li>Specify contracts for reward point calculations</li>
 * <li>Enable transaction filtering based on configurable cutoff dates</li>
 * <li>Support error handling through exception specifications</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Implementation Pattern:</b>
 * This interface promotes the Strategy pattern, allowing multiple
 * implementations of customer
 * service logic. The primary implementation is {@code CustomerServiceImpl},
 * which handles reward
 * calculations and monthly reward aggregations.
 * </p>
 * 
 * <p>
 * <b>Data Return Format:</b>
 * All methods return {@code CustomerDataResponse} objects (or lists thereof)
 * which include:
 * <ul>
 * <li>Customer identification and basic information</li>
 * <li>Calculated reward points and reward breakdowns</li>
 * <li>Monthly reward aggregations showing points earned by month</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Architecture Layer:</b>
 * This interface sits in the service layer, bridging the controller layer and
 * DAO layer:
 * 
 * <pre>
 * Controller Layer
 *     ↓
 * Service Layer (You are here)  ← CustomerService interface
 *     ↓
 * DAO Layer
 *     ↓
 * Repository Layer / Database
 * </pre>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.service.impl.CustomerServiceImpl
 * @see com.retailer.rewards.dto.CustomerDataResponse
 * @see com.retailer.rewards.dao.CustomerDao
 */
public interface CustomerService {

    /**
     * Retrieves all customers with their calculated reward information.
     * 
     * <p>
     * This method retrieves all customers from the database and enriches them with
     * reward
     * data, including calculated reward points and monthly reward breakdowns. The
     * method
     * automatically filters transactions based on a configurable lookback period to
     * include
     * only recent relevant transactions for reward calculations.
     * </p>
     * 
     * <p>
     * <b>Reward Calculation:</b>
     * For each customer, the implementation:
     * <ul>
     * <li>Retrieves all their transactions from the last N months
     * (configurable)</li>
     * <li>Calculates reward points for each transaction based on the amount</li>
     * <li>Aggregates rewards by month to provide monthly breakdowns</li>
     * <li>Calculates total reward points across all transactions</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Return Data Structure:</b>
     * Each {@code CustomerDataResponse} in the returned list contains:
     * <ul>
     * <li>Customer ID and name</li>
     * <li>Monthly rewards (list of month names and points earned in each
     * month)</li>
     * <li>Total reward points (sum of all monthly rewards)</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Error Handling:</b>
     * If no customers are found in the database, a
     * {@code CustomerNotFoundException}
     * is thrown to indicate the absence of customer data.
     * </p>
     * 
     * <p>
     * <b>Performance Characteristics:</b>
     * The implementation uses efficient database queries with eager loading of
     * transactions
     * to minimize the number of database round-trips.
     * </p>
     * 
     * @return a {@code List<CustomerDataResponse>} containing all customers with
     *         their reward
     *         information. Each element includes customer details, monthly rewards,
     *         and total points.
     *         Never returns null.
     * 
     * @throws com.retailer.rewards.exception.CustomerNotFoundException if no
     *                                                                  customers
     *                                                                  exist
     *                                                                  in the
     *                                                                  database or
     *                                                                  if no
     *                                                                  customer
     *                                                                  data can be
     *                                                                  retrieved
     * 
     * @see com.retailer.rewards.dto.CustomerDataResponse
     * @see com.retailer.rewards.exception.CustomerNotFoundException
     */
    List<CustomerDataResponse> getAllCustomers();

    /**
     * Retrieves a specific customer by their unique customer ID with calculated
     * reward information.
     * 
     * <p>
     * This method retrieves a single customer identified by their customer ID and
     * enriches them
     * with reward data, including calculated reward points and monthly reward
     * breakdowns. The method
     * automatically filters transactions based on a configurable lookback period to
     * include only
     * recent relevant transactions for reward calculations.
     * </p>
     * 
     * <p>
     * <b>Reward Calculation:</b>
     * For the retrieved customer, the implementation:
     * <ul>
     * <li>Retrieves all their transactions from the last N months
     * (configurable)</li>
     * <li>Calculates reward points for each transaction based on the amount</li>
     * <li>Aggregates rewards by month to provide monthly breakdowns</li>
     * <li>Calculates total reward points across all transactions</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Reward Points Calculation Rules:</b>
     * <ul>
     * <li>For amounts $0-$50: 0 reward points</li>
     * <li>For amounts $50-$100: 1 reward point per $1 spent above $50</li>
     * <li>For amounts above $100:
     * <ul>
     * <li>50 points for the $50-$100 range (1 point per $1)</li>
     * <li>2 reward points per $1 spent above $100</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Return Data Structure:</b>
     * The returned {@code CustomerDataResponse} contains:
     * <ul>
     * <li>Customer ID and name</li>
     * <li>Monthly rewards (list of month names and points earned in each
     * month)</li>
     * <li>Total reward points (sum of all monthly rewards)</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Error Handling:</b>
     * If no customer is found with the specified customer ID, a
     * {@code CustomerNotFoundException}
     * is thrown to indicate that the requested customer does not exist.
     * </p>
     * 
     * <p>
     * <b>Performance Characteristics:</b>
     * The implementation uses efficient database queries with eager loading of
     * transactions
     * to minimize the number of database round-trips.
     * </p>
     * 
     * <p>
     * <b>Usage Example:</b>
     * 
     * <pre>
     * {@code
     * try {
     *     CustomerDataResponse response = customerService.getCustomerById(123L);
     *     System.out.println("Customer: " + response.getCustomername());
     *     System.out.println("Total Points: " + response.getTotalPoints());
     *     System.out.println("Monthly Rewards: " + response.getMonthlyRewards());
     * } catch (CustomerNotFoundException e) {
     *     System.out.println("Customer not found");
     * }
     * }
     * </pre>
     * </p>
     * 
     * @param customerId the unique identifier of the customer to retrieve. Must be
     *                   a positive
     *                   long value representing a valid customer ID in the system.
     * 
     * @return a {@code CustomerDataResponse} containing the customer's details with
     *         calculated
     *         reward information including monthly rewards breakdown and total
     *         reward points.
     *         Never returns null.
     * 
     * @throws com.retailer.rewards.exception.CustomerNotFoundException if no
     *                                                                  customer is
     *                                                                  found
     *                                                                  with the
     *                                                                  specified
     *                                                                  customer ID
     *                                                                  in the
     *                                                                  database
     * 
     * @see com.retailer.rewards.dto.CustomerDataResponse
     * @see com.retailer.rewards.exception.CustomerNotFoundException
     */
    CustomerDataResponse getCustomerById(Long customerId);
}