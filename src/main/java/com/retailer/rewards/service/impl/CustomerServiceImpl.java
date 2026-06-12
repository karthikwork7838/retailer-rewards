package com.retailer.rewards.service.impl;

import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dto.CustomerDataResponse;
import com.retailer.rewards.dto.CustomerDto;
import com.retailer.rewards.dto.MonthlyReward;
import com.retailer.rewards.dto.TransactionDto;
import com.retailer.rewards.enums.MonthEnum;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for customer-related business operations in the
 * rewards program.
 * 
 * This class implements the {@code CustomerService} interface and provides the
 * core business logic
 * for customer data retrieval and reward point calculations. It orchestrates
 * between the data access
 * layer (DAO) and the presentation layer (controllers), handling customer
 * queries, reward calculations,
 * and monthly reward aggregations.
 * 
 * <p>
 * <b>Key Responsibilities:</b>
 * <ul>
 * <li>Retrieve all customers or a specific customer by ID</li>
 * <li>Filter customer transactions based on a configurable cutoff date (number
 * of months)</li>
 * <li>Calculate reward points based on transaction amounts using the reward
 * rules</li>
 * <li>Generate monthly reward summaries aggregating points by month</li>
 * <li>Handle error cases by throwing appropriate exceptions for missing
 * customers</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Reward Points Calculation Rules:</b>
 * <ul>
 * <li>For each $1 spent between $50-$100: 1 reward point</li>
 * <li>For each $1 spent above $100: 2 reward points</li>
 * <li>No reward points for transactions $50 or less</li>
 * </ul>
 * Example: A $120 transaction yields (50 * 1) + (20 * 2) = 90 reward points
 * </p>
 * 
 * <p>
 * <b>Configuration:</b>
 * The service uses an externally configurable property
 * ({@code transaction.months}) to determine
 * the cutoff date for filtering transactions. This allows flexibility in which
 * historical
 * transactions are considered for reward calculations.
 * </p>
 * 
 * <p>
 * <b>Data Flow:</b>
 * <ol>
 * <li>Controller calls service method</li>
 * <li>Service calculates cutoff date based on configuration</li>
 * <li>Service delegates to DAO to fetch customer data with transactions</li>
 * <li>Service processes transactions to calculate rewards</li>
 * <li>Service returns enriched response with reward data to controller</li>
 * </ol>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.service.CustomerService
 * @see com.retailer.rewards.dao.CustomerDao
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    /**
     * Data Access Object for customer-related database operations.
     * Used to fetch customer and transaction data from the database.
     */
    @Autowired
    private CustomerDao customerDao;

    /**
     * Number of months to consider when filtering customer transactions.
     * 
     * <p>
     * This configuration value determines the lookback period for transactions.
     * For example, if set to 3, transactions from the last 3 months will be
     * included.
     * Loaded from the application property: {@code transaction.months}
     * </p>
     */
    @Value("${transaction.months}")
    private int monthValue;

    /**
     * Retrieves all customers along with their reward information.
     * 
     * <p>
     * This method performs the following operations:
     * <ol>
     * <li>Calculates a cutoff date by subtracting the configured number of months
     * from today</li>
     * <li>Fetches all customers with transactions after the cutoff date via the
     * DAO</li>
     * <li>For each customer, calculates monthly rewards based on their
     * transactions</li>
     * <li>Aggregates total reward points across all transactions</li>
     * <li>Returns enriched customer data with reward information</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Cutoff Date Calculation:</b>
     * The cutoff date is determined as: Today - {@code transaction.months} months
     * For example, if today is June 12, 2026 and monthValue is 3, the cutoff date
     * is March 12, 2026. Only transactions on or after March 12 are considered.
     * </p>
     * 
     * <p>
     * <b>Error Handling:</b>
     * If no customers are found in the database, a
     * {@code CustomerNotFoundException}
     * is thrown with the message "Customers not found".
     * </p>
     * 
     * <p>
     * <b>Usage Example:</b>
     * 
     * <pre>
     * {@code
     * List<CustomerDataResponse> allCustomers = customerService.getAllCustomers();
     * for (CustomerDataResponse customer : allCustomers) {
     *     System.out.println("Customer: " + customer.getCustomername());
     *     System.out.println("Total Points: " + customer.getTotalPoints());
     *     System.out.println("Monthly Rewards: " + customer.getMonthlyRewards());
     * }
     * }
     * </pre>
     * </p>
     * 
     * @return a {@code List<CustomerDataResponse>} containing all customers with
     *         their
     *         calculated reward points and monthly reward breakdowns. Never returns
     *         null.
     * 
     * @throws CustomerNotFoundException if no customers are found in the database
     */
    @Override
    public List<CustomerDataResponse> getAllCustomers() {
        List<CustomerDataResponse> customerResponseDtos = new ArrayList<>();
        LocalDate cutoffDate = LocalDate.now().minusMonths(monthValue);
        List<CustomerDto> customerDataList = customerDao.getAllCustomers(cutoffDate);
        if (ObjectUtils.isEmpty(customerDataList)) {
            throw new CustomerNotFoundException("Customers not found");
        }
        for (CustomerDto customer : customerDataList) {
            CustomerDataResponse customerDataResponse = new CustomerDataResponse();
            customerDataResponse.setCustomerId(customer.getCustomerId());
            customerDataResponse.setCustomername(customer.getCustomername());
            updateCustomerWithRewardData(customerDataResponse, customer.getTransactionList());
            customerResponseDtos.add(customerDataResponse);
        }
        return customerResponseDtos;
    }

    /**
     * Retrieves a specific customer by their unique customer ID with reward
     * information.
     * 
     * <p>
     * This method performs the following operations:
     * <ol>
     * <li>Calculates a cutoff date by subtracting the configured number of months
     * from today</li>
     * <li>Fetches the customer with the specified ID and transactions after the
     * cutoff date via the DAO</li>
     * <li>Validates that a customer was found; throws exception if not</li>
     * <li>Calculates monthly rewards based on the customer's transactions</li>
     * <li>Aggregates total reward points across all transactions</li>
     * <li>Returns enriched customer data with reward information</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Cutoff Date Calculation:</b>
     * The cutoff date is determined as: Today - {@code transaction.months} months
     * For example, if today is June 12, 2026 and monthValue is 3, the cutoff date
     * is March 12, 2026. Only transactions on or after March 12 are considered.
     * </p>
     * 
     * <p>
     * <b>Error Handling:</b>
     * If the customer is not found or if the DAO returns an empty customer DTO,
     * a {@code CustomerNotFoundException} is thrown with the message
     * "Customer not found with id: [customerId]".
     * </p>
     * 
     * <p>
     * <b>Usage Example:</b>
     * 
     * <pre>
     * {@code
     * try {
     *     CustomerDataResponse customer = customerService.getCustomerById(123L);
     *     System.out.println("Customer: " + customer.getCustomername());
     *     System.out.println("Total Points: " + customer.getTotalPoints());
     *     System.out.println("Monthly Rewards: " + customer.getMonthlyRewards());
     * } catch (CustomerNotFoundException e) {
     *     System.out.println("Customer not found: " + e.getMessage());
     * }
     * }
     * </pre>
     * </p>
     * 
     * @param customerId the unique identifier of the customer to retrieve. Must be
     *                   a positive long value.
     * 
     * @return a {@code CustomerDataResponse} containing the customer's details with
     *         their
     *         calculated reward points and monthly reward breakdowns. Never returns
     *         null.
     * 
     * @throws CustomerNotFoundException if no customer is found with the specified
     *                                   ID
     */
    @Override
    public CustomerDataResponse getCustomerById(Long customerId) {
        CustomerDataResponse customerResponse = new CustomerDataResponse();
        LocalDate cutoffDate = LocalDate.now().minusMonths(monthValue);
        CustomerDto customerDto = customerDao.getCustomerById(customerId, cutoffDate);
        if (ObjectUtils.isEmpty(customerDto) || ObjectUtils.isEmpty(customerDto.getCustomerId())
                || ObjectUtils.isEmpty(customerDto.getCustomername())) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId, 404);
        }
        customerResponse.setCustomerId(customerDto.getCustomerId());
        customerResponse.setCustomername(customerDto.getCustomername());
        return updateCustomerWithRewardData(customerResponse, customerDto.getTransactionList());
    }

    /**
     * Enriches a customer response with calculated reward data.
     * 
     * <p>
     * This private method processes a list of transactions and performs the
     * following operations:
     * <ol>
     * <li>Calculates reward points for each transaction using the reward rules</li>
     * <li>Groups transactions by month using the transaction date</li>
     * <li>Creates {@code MonthlyReward} objects for each month</li>
     * <li>Aggregates total reward points across all transactions</li>
     * <li>Sets the monthly rewards and total points on the response object</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Monthly Aggregation:</b>
     * Each transaction is converted to a {@code MonthlyReward} entry containing:
     * <ul>
     * <li>Month name (e.g., "January", "February") derived from transaction
     * date</li>
     * <li>Reward points calculated based on transaction amount</li>
     * </ul>
     * Multiple transactions in the same month result in multiple monthly reward
     * entries.
     * </p>
     * 
     * <p>
     * <b>Null Safety:</b>
     * If the transaction list is null or empty, the customer response's monthly
     * rewards
     * and total points are left unchanged (typically null or unset).
     * </p>
     * 
     * <p>
     * <b>Usage (Internal):</b>
     * This method is called internally by getAllCustomers() and getCustomerById()
     * to enrich the customer response with reward information before returning to
     * the caller.
     * </p>
     * 
     * @param customerDataResponse the customer response object to enrich with
     *                             reward data.
     *                             This object will be modified by adding monthly
     *                             rewards and total points.
     *                             Must not be null.
     * @param transactionList      the list of transactions to process and calculate
     *                             rewards for.
     *                             May be null or empty, in which case no rewards
     *                             are added.
     * 
     * @return the enriched {@code CustomerDataResponse} object with monthly rewards
     *         and total points populated.
     *         Returns the same object that was passed as parameter.
     */
    private CustomerDataResponse updateCustomerWithRewardData(CustomerDataResponse customerDataResponse,
            List<TransactionDto> transactionList) {
        if (!ObjectUtils.isEmpty(transactionList)) {
            List<MonthlyReward> monthlyRewards = transactionList.stream()
                    .map(trx -> new MonthlyReward(
                            MonthEnum.getMonthNameByNumber(trx.getTransactionDate().getMonth().getValue()),
                            calculateRewardPoints(trx.getTransactionAmount())))
                    .toList();
            BigDecimal totalPoints = monthlyRewards.stream().map(MonthlyReward::getPoints).reduce(BigDecimal.ZERO,
                    BigDecimal::add);
            customerDataResponse.setMonthlyRewards(monthlyRewards);
            customerDataResponse.setTotalPoints(totalPoints);
        }
        return customerDataResponse;
    }

    /**
     * Calculates reward points for a given transaction amount.
     * 
     * <p>
     * <b>Reward Points Rules:</b>
     * <ul>
     * <li>For amounts $0-$50: 0 reward points</li>
     * <li>For amounts $50-$100: 1 reward point per $1 spent above $50</li>
     * <li>For amounts above $100:
     * <ul>
     * <li>1 reward point per $1 spent between $50-$100 = 50 points</li>
     * <li>2 reward points per $1 spent above $100</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Calculation Examples:</b>
     * <ul>
     * <li>$30 transaction: 0 points (below $50 threshold)</li>
     * <li>$75 transaction: 25 points (75 - 50 = 25 * 1)</li>
     * <li>$150 transaction: 150 points ((100 - 50) * 1 + (150 - 100) * 2 = 50 + 100
     * = 150)</li>
     * <li>$120 transaction: 90 points ((100 - 50) * 1 + (120 - 100) * 2 = 50 + 40 =
     * 90)</li>
     * </ul>
     * </p>
     * 
     * <p>
     * <b>Algorithm Flow:</b>
     * <ol>
     * <li>Check if amount exceeds $100; if yes, add (amount - 100) * 2 points to
     * total</li>
     * <li>Cap remaining amount to $100</li>
     * <li>Check if remaining amount exceeds $50; if yes, add (amount - 50) * 1
     * points to total</li>
     * <li>Return total reward points as BigDecimal</li>
     * </ol>
     * </p>
     * 
     * <p>
     * <b>Implementation Notes:</b>
     * <ul>
     * <li>Uses {@code BigDecimal} for input to ensure financial precision</li>
     * <li>Performs integer arithmetic for point calculation using
     * {@code intValue()}</li>
     * <li>Returns result as {@code BigDecimal} for consistency with monetary
     * operations</li>
     * </ul>
     * </p>
     * 
     * @param amount the transaction amount in dollars as a {@code BigDecimal}. Must
     *               be non-negative.
     * 
     * @return the calculated reward points as a {@code BigDecimal}. Will be a whole
     *         number
     *         (no decimal portion) representing reward points earned from this
     *         transaction.
     */
    private BigDecimal calculateRewardPoints(BigDecimal amount) {
        int rewardPoints = 0;
        if (amount.compareTo(BigDecimal.valueOf(100)) > 0) {
            rewardPoints += amount
                    .subtract(BigDecimal.valueOf(100))
                    .multiply(BigDecimal.valueOf(2))
                    .intValue();
            amount = BigDecimal.valueOf(100);
        }
        if (amount.compareTo(BigDecimal.valueOf(50)) > 0) {
            rewardPoints += amount
                    .subtract(BigDecimal.valueOf(50))
                    .intValue();
        }
        return BigDecimal.valueOf(rewardPoints);
    }
}