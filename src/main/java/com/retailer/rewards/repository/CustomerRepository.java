package com.retailer.rewards.repository;

import com.retailer.rewards.entity.CustomerEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for customer entity persistence operations.
 * 
 * This repository provides database access methods for the
 * {@code CustomerEntity} class,
 * enabling CRUD (Create, Read, Update, Delete) operations and custom query
 * execution.
 * It extends {@code JpaRepository} to inherit standard CRUD functionality while
 * adding
 * custom query methods specific to the rewards application's business logic.
 * 
 * <p>
 * <b>Responsibilities:</b>
 * <ul>
 * <li>Provide database access for customer entities and their associated
 * transactions</li>
 * <li>Execute custom JPQL queries for complex data retrieval scenarios</li>
 * <li>Support date-based filtering to retrieve transactions after a cutoff
 * date</li>
 * <li>Handle eager loading of related transaction entities to avoid N+1 query
 * problems</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Architecture Layer:</b>
 * This repository sits in the persistence layer and is typically accessed
 * through
 * the DAO layer ({@code CustomerDaoImpl}). The DAO layer abstracts the
 * repository
 * details from the service layer, providing an additional layer of abstraction.
 * </p>
 * 
 * <p>
 * <b>Custom Query Features:</b>
 * <ul>
 * <li>Uses {@code LEFT JOIN FETCH} to eagerly load transactions, preventing
 * lazy loading issues</li>
 * <li>Uses {@code DISTINCT} to handle one-to-many relationships and avoid
 * duplicate customer records</li>
 * <li>Supports dynamic filtering with optional cutoff dates using conditional
 * parameters</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Data Access Pattern:</b>
 * 
 * <pre>
 * DAO Layer (CustomerDaoImpl)
 *     ↓
 * Repository (CustomerRepository)  ← You are here
 *     ↓
 * Database Tables (customer, transactions)
 * </pre>
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see com.retailer.rewards.entity.CustomerEntity
 * @see com.retailer.rewards.dao.impl.CustomerDaoImpl
 */
@Repository
public interface CustomerRepository
                extends JpaRepository<CustomerEntity, Long> {

        /**
         * Retrieves a specific customer by ID along with their transactions after a
         * cutoff date.
         * 
         * <p>
         * <b>Query Strategy:</b>
         * This method uses a custom JPQL query with the following features:
         * <ul>
         * <li>{@code LEFT JOIN FETCH}: Eagerly loads the customer's transaction
         * collection
         * to prevent lazy loading and N+1 query problems</li>
         * <li>{@code DISTINCT}: Eliminates duplicate customer records that can occur
         * when
         * joining with multiple transactions</li>
         * <li>Conditional filtering: Filters transactions based on the cutoff date
         * parameter
         * (if provided)</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>Cutoff Date Behavior:</b>
         * <ul>
         * <li>If {@code cutoffDate} is {@code null}, all transactions for the customer
         * are included</li>
         * <li>If {@code cutoffDate} is provided, only transactions with dates >=
         * cutoffDate are included</li>
         * <li>The cutoff date enables filtering of recent transactions for reward
         * calculations</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>Performance Considerations:</b>
         * <ul>
         * <li>Uses eager loading (FETCH JOIN) to load transactions in a single
         * query</li>
         * <li>Uses DISTINCT to handle one-to-many relationships correctly</li>
         * <li>More efficient than lazy loading which would trigger additional queries
         * per transaction</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>Usage Example:</b>
         * 
         * <pre>
         * {@code
         * LocalDate cutoffDate = LocalDate.now().minusMonths(1);
         * Optional<CustomerEntity> customer = customerRepository
         *                 .findCustomerWithTransactions(123L, cutoffDate);
         * 
         * if (customer.isPresent()) {
         *         CustomerEntity entity = customer.get();
         *         // Access entity.getTransactions() - already loaded, no additional query
         * }
         * }
         * </pre>
         * </p>
         * 
         * @param customerId the unique identifier of the customer to retrieve. Must be
         *                   a positive long value.
         * @param cutoffDate the date threshold for filtering transactions. Transactions
         *                   with dates
         *                   greater than or equal to this date will be included. If
         *                   {@code null},
         *                   all transactions are included.
         * 
         * @return an {@code Optional<CustomerEntity>} containing the customer with
         *         their filtered transactions
         *         if found. The Optional will be empty if no customer exists with the
         *         provided ID.
         *         The customer entity will have its transactions eagerly loaded (not
         *         lazy-loaded).
         */
        @Query("SELECT DISTINCT c FROM CustomerEntity c LEFT JOIN FETCH c.transactions t WHERE c.customerId = :customerId AND (:cutoffDate IS NULL OR t.transactionDate >= :cutoffDate)")
        Optional<CustomerEntity> findCustomerWithTransactions(@Param("customerId") Long customerId,
                        @Param("cutoffDate") LocalDate cutoffDate);

        /**
         * Retrieves all customers along with their transactions after a cutoff date.
         * 
         * <p>
         * <b>Query Strategy:</b>
         * This method uses a custom JPQL query with the following features:
         * <ul>
         * <li>{@code LEFT JOIN FETCH}: Eagerly loads all customers' transaction
         * collections
         * to prevent lazy loading and N+1 query problems</li>
         * <li>{@code DISTINCT}: Eliminates duplicate customer records that can occur
         * when
         * joining with multiple transactions</li>
         * <li>Conditional filtering: Filters transactions based on the cutoff date
         * parameter
         * (if provided)</li>
         * <li>Complex WHERE clause: Handles edge cases including customers with no
         * transactions</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>WHERE Clause Logic:</b>
         * The condition
         * {@code (t IS NULL OR :cutoffDate IS NULL OR t.transactionDate >= :cutoffDate)}
         * handles:
         * <ul>
         * <li>{@code t IS NULL}: Includes customers who have no transactions (LEFT JOIN
         * result)</li>
         * <li>{@code :cutoffDate IS NULL}: If no cutoff date is provided, includes all
         * transactions</li>
         * <li>{@code t.transactionDate >= :cutoffDate}: Only includes transactions
         * after the cutoff date</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>Cutoff Date Behavior:</b>
         * <ul>
         * <li>If {@code cutoffDate} is {@code null}, all customers with all their
         * transactions are returned</li>
         * <li>If {@code cutoffDate} is provided, returns all customers with only their
         * transactions
         * having dates >= cutoffDate</li>
         * <li>The cutoff date enables bulk filtering of recent transactions for monthly
         * reward calculations</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>Performance Considerations:</b>
         * <ul>
         * <li>Uses eager loading (FETCH JOIN) to load all transactions in a single
         * query</li>
         * <li>Uses DISTINCT to handle one-to-many relationships correctly</li>
         * <li>More efficient than lazy loading which would trigger additional queries
         * per customer/transaction</li>
         * <li>For large datasets, consider pagination to avoid loading excessive data
         * into memory</li>
         * </ul>
         * </p>
         * 
         * <p>
         * <b>Usage Example:</b>
         * 
         * <pre>
         * {@code
         * // Get all customers with all transactions
         * List<CustomerEntity> allCustomers = customerRepository
         *                 .findAllCustomersWithTransactions(null);
         * 
         * // Get all customers with transactions from the last 30 days
         * LocalDate cutoffDate = LocalDate.now().minusDays(30);
         * List<CustomerEntity> recentCustomers = customerRepository
         *                 .findAllCustomersWithTransactions(cutoffDate);
         * 
         * // Transactions are already loaded, no additional queries needed
         * for (CustomerEntity customer : recentCustomers) {
         *         List<TransactionEntity> transactions = customer.getTransactions();
         * }
         * }
         * </pre>
         * </p>
         * 
         * @param cutoffDate the date threshold for filtering transactions. Transactions
         *                   with dates
         *                   greater than or equal to this date will be included. If
         *                   {@code null},
         *                   all customers and their transactions are included.
         * 
         * @return a {@code List<CustomerEntity>} containing all customers with their
         *         filtered transactions.
         *         The list will be empty if no customers exist in the database. Each
         *         customer entity
         *         will have its transactions eagerly loaded (not lazy-loaded). Never
         *         returns null.
         */
        @Query("SELECT DISTINCT c FROM CustomerEntity c LEFT JOIN FETCH c.transactions t WHERE t IS NULL OR :cutoffDate IS NULL OR t.transactionDate >= :cutoffDate")
        List<CustomerEntity> findAllCustomersWithTransactions(@Param("cutoffDate") LocalDate cutoffDate);
}
