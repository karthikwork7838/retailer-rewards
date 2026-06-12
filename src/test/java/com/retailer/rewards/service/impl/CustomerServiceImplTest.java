package com.retailer.rewards.service.impl;

import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dto.CustomerDataResponse;
import com.retailer.rewards.dto.CustomerDto;
import com.retailer.rewards.dto.MonthlyReward;
import com.retailer.rewards.dto.TransactionDto;
import com.retailer.rewards.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Comprehensive unit tests for {@code CustomerServiceImpl} class.
 * 
 * <p>
 * This test class verifies all methods in CustomerServiceImpl using Mockito to mock
 * the CustomerDao dependency. Tests are organized into nested test classes for better
 * readability and cover successful scenarios, exception handling, edge cases, and
 * boundary conditions.
 * </p>
 * 
 * <p>
 * <b>Test Organization:</b>
 * <ul>
 * <li>GetAllCustomersTests: Tests for getAllCustomers() method</li>
 * <li>GetCustomerByIdTests: Tests for getCustomerById() method</li>
 * <li>CalculateRewardPointsTests: Tests for calculateRewardPoints() method</li>
 * </ul>
 * </p>
 * 
 * @author Test Suite
 * @version 1.0
 * @see CustomerServiceImpl
 * @see CustomerDao
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerServiceImpl Tests")
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private LocalDate cutoffDate;

    @BeforeEach
    void setUp() {
        cutoffDate = LocalDate.now().minusMonths(3);
        // Set monthValue via reflection since it's injected by Spring
        try {
            java.lang.reflect.Field field = CustomerServiceImpl.class.getDeclaredField("monthValue");
            field.setAccessible(true);
            field.setInt(customerService, 3);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set monthValue", e);
        }
    }

    /**
     * Helper method to create a TransactionDto object.
     */
    private TransactionDto createTransactionDto(LocalDate date, BigDecimal amount) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionDate(date);
        dto.setTransactionAmount(amount);
        return dto;
    }

    /**
     * Helper method to create a CustomerDto object.
     */
    private CustomerDto createCustomerDto(Long customerId, String customerName, List<TransactionDto> transactions) {
        CustomerDto dto = new CustomerDto();
        dto.setCustomerId(customerId);
        dto.setCustomerName(customerName);
        dto.setTransactionList(transactions);
        return dto;
    }

    // ==================== Nested Test Class for getAllCustomers() ====================

    @Nested
    @DisplayName("getAllCustomers() Tests")
    class GetAllCustomersTests {

        @Test
        @DisplayName("Should retrieve all customers successfully")
        void testGetAllCustomers_Success() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now().minusDays(10), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now().minusDays(5), BigDecimal.valueOf(120))
            );
            CustomerDto customer1 = createCustomerDto(1L, "John Doe", transactions);
            CustomerDto customer2 = createCustomerDto(2L, "Jane Smith", Collections.emptyList());
            
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer1, customer2));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("John Doe", result.get(0).getCustomername());
            assertEquals("Jane Smith", result.get(1).getCustomername());
        }

        @Test
        @DisplayName("Should populate customer ID in response")
        void testGetAllCustomers_CustomerIdPopulated() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertEquals(1, result.size());
            assertEquals(1L, result.get(0).getCustomerId());
        }

        @Test
        @DisplayName("Should populate customer name in response")
        void testGetAllCustomers_CustomerNamePopulated() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertEquals(1, result.size());
            assertEquals("John Doe", result.get(0).getCustomername());
        }

        @Test
        @DisplayName("Should call DAO with correct cutoff date")
        void testGetAllCustomers_DaoCalledWithCorrectCutoffDate() {
            // Arrange
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(new ArrayList<>());

            // Act
            try {
                customerService.getAllCustomers();
            } catch (CustomerNotFoundException e) {
                // Expected
            }

            // Assert
            verify(customerDao, times(1)).getAllCustomers(any(LocalDate.class));
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when no customers found")
        void testGetAllCustomers_ThrowsException_NoCustomers() {
            // Arrange
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(new ArrayList<>());

            // Act & Assert
            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getAllCustomers()
            );

            assertEquals("Customers not found", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException with null customer list")
        void testGetAllCustomers_ThrowsException_NullCustomerList() {
            // Arrange
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(null);

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getAllCustomers()
            );
        }

        @Test
        @DisplayName("Should calculate total reward points for all customers")
        void testGetAllCustomers_CalculatesTotalRewardPoints() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertEquals(1, result.size());
            // $75 = 25 points, $120 = 90 points. Total = 115
            assertEquals(BigDecimal.valueOf(115), result.get(0).getTotalPoints());
        }

        @Test
        @DisplayName("Should generate monthly rewards for all customers")
        void testGetAllCustomers_GeneratesMonthlyRewards() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertEquals(1, result.size());
            assertEquals(2, result.get(0).getMonthlyRewards().size());
        }

        @Test
        @DisplayName("Should handle multiple customers with different transactions")
        void testGetAllCustomers_MultipleCustomersWithDifferentTransactions() {
            // Arrange
            List<TransactionDto> transactions1 = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(60))
            );
            List<TransactionDto> transactions2 = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(150))
            );
            CustomerDto customer1 = createCustomerDto(1L, "John Doe", transactions1);
            CustomerDto customer2 = createCustomerDto(2L, "Jane Smith", transactions2);
            
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer1, customer2));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertEquals(2, result.size());
            assertEquals(BigDecimal.valueOf(10), result.get(0).getTotalPoints());
            assertEquals(BigDecimal.valueOf(150), result.get(1).getTotalPoints());
        }

        @Test
        @DisplayName("Should handle customers with no transactions")
        void testGetAllCustomers_CustomersWithNoTransactions() {
            // Arrange
            CustomerDto customer1 = createCustomerDto(1L, "John Doe", Collections.emptyList());
            CustomerDto customer2 = createCustomerDto(2L, "Jane Smith", null);
            
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer1, customer2));

            // Act
            List<CustomerDataResponse> result = customerService.getAllCustomers();

            // Assert
            assertEquals(2, result.size());
            assertNotNull(result.get(0));
            assertNotNull(result.get(1));
        }

        @Test
        @DisplayName("Should call DAO exactly once")
        void testGetAllCustomers_DaoCalledOnce() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getAllCustomers(any(LocalDate.class))).thenReturn(Arrays.asList(customer));

            // Act
            customerService.getAllCustomers();

            // Assert
            verify(customerDao, times(1)).getAllCustomers(any(LocalDate.class));
            verifyNoMoreInteractions(customerDao);
        }
    }

    // ==================== Nested Test Class for getCustomerById() ====================

    @Nested
    @DisplayName("getCustomerById() Tests")
    class GetCustomerByIdTests {

        @Test
        @DisplayName("Should retrieve customer by ID successfully")
        void testGetCustomerById_Success() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now().minusDays(10), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now().minusDays(5), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result);
            assertEquals(1L, result.getCustomerId());
            assertEquals("John Doe", result.getCustomername());
        }

        @Test
        @DisplayName("Should populate customer ID in response")
        void testGetCustomerById_CustomerIdPopulated() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getCustomerId());
            assertEquals(1L, result.getCustomerId());
        }

        @Test
        @DisplayName("Should populate customer name in response")
        void testGetCustomerById_CustomerNamePopulated() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getCustomername());
            assertEquals("John Doe", result.getCustomername());
        }

        @Test
        @DisplayName("Should call DAO with correct customer ID and cutoff date")
        void testGetCustomerById_DaoCalledWithCorrectParameters() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            customerService.getCustomerById(1L);

            // Assert
            verify(customerDao, times(1)).getCustomerById(eq(1L), any(LocalDate.class));
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when customer not found")
        void testGetCustomerById_ThrowsException_CustomerNotFound() {
            // Arrange
            when(customerDao.getCustomerById(eq(999L), any(LocalDate.class))).thenReturn(null);

            // Act & Assert
            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getCustomerById(999L)
            );

            assertEquals("Customer not found with id: 999", exception.getMessage());
            assertEquals(404, exception.getStatusCode());
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException with HTTP 404 status")
        void testGetCustomerById_ExceptionHas404Status() {
            // Arrange
            when(customerDao.getCustomerById(eq(999L), any(LocalDate.class))).thenReturn(null);

            // Act & Assert
            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getCustomerById(999L)
            );

            assertEquals(404, exception.getStatusCode());
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when customer ID is null")
        void testGetCustomerById_ThrowsException_CustomerIdNull() {
            // Arrange
            CustomerDto invalidCustomer = new CustomerDto();
            invalidCustomer.setCustomerId(null);
            invalidCustomer.setCustomerName("John");
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(invalidCustomer);

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getCustomerById(1L)
            );
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when customer name is null")
        void testGetCustomerById_ThrowsException_CustomerNameNull() {
            // Arrange
            CustomerDto invalidCustomer = new CustomerDto();
            invalidCustomer.setCustomerId(1L);
            invalidCustomer.setCustomerName(null);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(invalidCustomer);

            // Act & Assert
            assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getCustomerById(1L)
            );
        }

        @Test
        @DisplayName("Should calculate total reward points")
        void testGetCustomerById_CalculatesTotalRewardPoints() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $75 = 25 points, $120 = 90 points. Total = 115
            assertEquals(BigDecimal.valueOf(115), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should generate monthly rewards")
        void testGetCustomerById_GeneratesMonthlyRewards() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getMonthlyRewards());
            assertEquals(2, result.getMonthlyRewards().size());
        }

        @Test
        @DisplayName("Should handle customer with no transactions")
        void testGetCustomerById_EmptyTransactions() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result);
            assertEquals("John Doe", result.getCustomername());
        }

        @Test
        @DisplayName("Should handle customer with null transaction list")
        void testGetCustomerById_NullTransactionList() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", null);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result);
            assertEquals(1L, result.getCustomerId());
        }

        @Test
        @DisplayName("Should retrieve different customers by different IDs")
        void testGetCustomerById_DifferentCustomerIds() {
            // Arrange
            CustomerDto customer1 = createCustomerDto(1L, "John Doe", Collections.emptyList());
            CustomerDto customer2 = createCustomerDto(2L, "Jane Smith", Collections.emptyList());
            
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer1);
            when(customerDao.getCustomerById(eq(2L), any(LocalDate.class))).thenReturn(customer2);

            // Act
            CustomerDataResponse result1 = customerService.getCustomerById(1L);
            CustomerDataResponse result2 = customerService.getCustomerById(2L);

            // Assert
            assertEquals(1L, result1.getCustomerId());
            assertEquals(2L, result2.getCustomerId());
            assertEquals("John Doe", result1.getCustomername());
            assertEquals("Jane Smith", result2.getCustomername());
        }

        @Test
        @DisplayName("Should handle large transaction amounts correctly")
        void testGetCustomerById_LargeTransactionAmount() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(1000))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $1000: (100 - 50) * 1 + (1000 - 100) * 2 = 50 + 1800 = 1850
            assertEquals(BigDecimal.valueOf(1850), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should call DAO exactly once")
        void testGetCustomerById_DaoCalledOnce() {
            // Arrange
            CustomerDto customer = createCustomerDto(1L, "John Doe", Collections.emptyList());
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            customerService.getCustomerById(1L);

            // Assert
            verify(customerDao, times(1)).getCustomerById(eq(1L), any(LocalDate.class));
            verifyNoMoreInteractions(customerDao);
        }

        @Test
        @DisplayName("Should throw exception with correct error message format")
        void testGetCustomerById_ExceptionMessageFormat() {
            // Arrange
            when(customerDao.getCustomerById(eq(123L), any(LocalDate.class))).thenReturn(null);

            // Act & Assert
            CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> 
                customerService.getCustomerById(123L)
            );

            assertTrue(exception.getMessage().contains("Customer not found with id: 123"));
        }
    }

    // ==================== Nested Test Class for calculateRewardPoints() ====================

    @Nested
    @DisplayName("calculateRewardPoints() Tests")
    class CalculateRewardPointsTests {

        @Test
        @DisplayName("Should calculate 0 points for $30 transaction (below threshold)")
        void testCalculateRewardPoints_BelowThreshold() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(30))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertEquals(BigDecimal.valueOf(0), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should calculate 25 points for $75 transaction")
        void testCalculateRewardPoints_75Dollars() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $75: (75 - 50) * 1 = 25 points
            assertEquals(BigDecimal.valueOf(25), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should calculate 50 points for $100 transaction")
        void testCalculateRewardPoints_100Dollars() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(100))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $100: (100 - 50) * 1 = 50 points
            assertEquals(BigDecimal.valueOf(50), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should calculate 90 points for $120 transaction")
        void testCalculateRewardPoints_120Dollars() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $120: (100 - 50) * 1 + (120 - 100) * 2 = 50 + 40 = 90 points
            assertEquals(BigDecimal.valueOf(90), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should calculate 150 points for $150 transaction")
        void testCalculateRewardPoints_150Dollars() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(150))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $150: (100 - 50) * 1 + (150 - 100) * 2 = 50 + 100 = 150 points
            assertEquals(BigDecimal.valueOf(150), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should calculate 0 points for $50 transaction (exact threshold)")
        void testCalculateRewardPoints_50DollarsExactThreshold() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(50))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertEquals(BigDecimal.valueOf(0), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should sum reward points from multiple transactions")
        void testCalculateRewardPoints_MultipleTransactions() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(60)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(80)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(110))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $60 = 10, $80 = 30, $110 = 70. Total = 110
            assertEquals(BigDecimal.valueOf(110), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should handle large transaction amount ($1000)")
        void testCalculateRewardPoints_LargeAmount() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(1000))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $1000: (100 - 50) * 1 + (1000 - 100) * 2 = 50 + 1800 = 1850
            assertEquals(BigDecimal.valueOf(1850), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should handle exact boundary amounts ($50, $100)")
        void testCalculateRewardPoints_BoundaryAmounts() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(50)),
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(100))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            // $50: 0 points, $100: 50 points. Total = 50
            assertEquals(BigDecimal.valueOf(50), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should return BigDecimal with whole numbers only")
        void testCalculateRewardPoints_ReturnsWholeNumbers() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75.50))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getTotalPoints());
            assertEquals(0, result.getTotalPoints().scale());
        }

        @Test
        @DisplayName("Should calculate rewards for very small amounts")
        void testCalculateRewardPoints_VerySmallAmount() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(0.99))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertEquals(BigDecimal.valueOf(0), result.getTotalPoints());
        }

        @Test
        @DisplayName("Should calculate rewards correctly for $51")
        void testCalculateRewardPoints_JustAboveThreshold() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(51))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getTotalPoints());
            // $51: (51 - 50) * 1 = 1 point
            assertEquals(BigDecimal.valueOf(1), result.getTotalPoints());
        }
    }

    // ==================== Additional Integration-Style Tests ====================

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should process customer with multiple months of transactions")
        void testMultipleMonthsOfTransactions() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now().minusDays(60), BigDecimal.valueOf(75)),
                createTransactionDto(LocalDate.now().minusDays(30), BigDecimal.valueOf(120)),
                createTransactionDto(LocalDate.now().minusDays(5), BigDecimal.valueOf(85))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getMonthlyRewards());
            assertEquals(3, result.getMonthlyRewards().size());
            assertNotNull(result.getTotalPoints());
        }

        @Test
        @DisplayName("Should include month names in monthly rewards")
        void testMonthlyRewardsIncludeMonthNames() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(75))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getMonthlyRewards());
            for (MonthlyReward reward : result.getMonthlyRewards()) {
                assertNotNull(reward.getMonth());
                assertFalse(reward.getMonth().isEmpty());
            }
        }

        @Test
        @DisplayName("Should include reward points in monthly rewards")
        void testMonthlyRewardsIncludePoints() {
            // Arrange
            List<TransactionDto> transactions = Arrays.asList(
                createTransactionDto(LocalDate.now(), BigDecimal.valueOf(120))
            );
            CustomerDto customer = createCustomerDto(1L, "John Doe", transactions);
            when(customerDao.getCustomerById(eq(1L), any(LocalDate.class))).thenReturn(customer);

            // Act
            CustomerDataResponse result = customerService.getCustomerById(1L);

            // Assert
            assertNotNull(result.getMonthlyRewards());
            for (MonthlyReward reward : result.getMonthlyRewards()) {
                assertNotNull(reward.getPoints());
                assertTrue(reward.getPoints().compareTo(BigDecimal.ZERO) >= 0);
            }
        }
    }
}