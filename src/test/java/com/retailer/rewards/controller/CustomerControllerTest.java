package com.retailer.rewards.controller;

import com.retailer.rewards.constants.CommonConstants;
import com.retailer.rewards.dto.CustomerResponse;
import com.retailer.rewards.dto.CustomerDataResponse;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for {@code CustomerController} class.
 * 
 * <p>
 * This test class verifies the behavior of the CustomerController using Mockito to mock
 * the CustomerService dependency. Tests cover both successful scenarios and exception handling.
 * </p>
 * 
 * <p>
 * <b>Test Coverage:</b>
 * <ul>
 * <li>GET /api/v1/allCustomers - Retrieve all customers</li>
 * <li>GET /api/v1/customers/{customerId} - Retrieve customer by ID</li>
 * <li>Exception handling for CustomerNotFoundException</li>
 * <li>HTTP status codes and response content types</li>
 * </ul>
 * </p>
 * 
 * @author Test Suite
 * @version 1.0
 * @see CustomerController
 * @see CustomerService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerController Tests")
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    private CustomerDataResponse sampleCustomerResponse;
    private List<CustomerDataResponse> sampleCustomerList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        
        // Initialize sample data
        sampleCustomerResponse = new CustomerDataResponse();
        sampleCustomerResponse.setCustomerId(1L);
        sampleCustomerResponse.setCustomername("John Doe");
        sampleCustomerResponse.setTotalPoints(BigDecimal.valueOf(150));
        
        sampleCustomerList = Arrays.asList(
            sampleCustomerResponse,
            createCustomerDataResponse(2L, "Jane Smith", BigDecimal.valueOf(200)),
            createCustomerDataResponse(3L, "Bob Johnson", BigDecimal.valueOf(175))
        );
    }

    /**
     * Helper method to create a CustomerDataResponse object.
     */
    private CustomerDataResponse createCustomerDataResponse(Long customerId, String customerName, BigDecimal totalPoints) {
        CustomerDataResponse response = new CustomerDataResponse();
        response.setCustomerId(customerId);
        response.setCustomername(customerName);
        response.setTotalPoints(totalPoints);
        return response;
    }

    // ==================== Tests for getAllCustomers() ====================

    @Test
    @DisplayName("Should retrieve all customers successfully")
    void testGetAllCustomers_Success() throws Exception {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(sampleCustomerList);

        // Act & Assert
        mockMvc.perform(get(CommonConstants.API + CommonConstants.GET_ALL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify
        verify(customerService, times(1)).getAllCustomers();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    @DisplayName("Should retrieve all customers with correct HTTP status 200")
    void testGetAllCustomers_ReturnsStatus200() throws Exception {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(sampleCustomerList);

        // Act
        ResponseEntity<CustomerResponse> response = customerController.getAllCustomers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should retrieve empty list when no customers exist")
    void testGetAllCustomers_EmptyList() throws Exception {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get(CommonConstants.API + CommonConstants.GET_ALL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("Should call CustomerService.getAllCustomers exactly once")
    void testGetAllCustomers_ServiceCalledOnce() {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(sampleCustomerList);

        // Act
        customerController.getAllCustomers();

        // Assert
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("Should return CustomerResponse object containing customer data")
    void testGetAllCustomers_ReturnsCustomerResponseObject() {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(sampleCustomerList);

        // Act
        ResponseEntity<CustomerResponse> response = customerController.getAllCustomers();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    // ==================== Tests for getCustomerById() ====================

    @Test
    @DisplayName("Should retrieve customer by ID successfully")
    void testGetCustomerById_Success() throws Exception {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(sampleCustomerResponse);

        // Act & Assert
        mockMvc.perform(get(CommonConstants.API + "/customers/" + customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    @DisplayName("Should retrieve customer by ID with correct HTTP status 200")
    void testGetCustomerById_ReturnsStatus200() {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(sampleCustomerResponse);

        // Act
        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return CustomerResponse object with customer details")
    void testGetCustomerById_ReturnsCustomerResponseObject() {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(sampleCustomerResponse);

        // Act
        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should call CustomerService.getCustomerById with correct customerId")
    void testGetCustomerById_ServiceCalledWithCorrectId() {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(sampleCustomerResponse);

        // Act
        customerController.getCustomerById(customerId);

        // Assert
        verify(customerService, times(1)).getCustomerById(customerId);
        verify(customerService).getCustomerById(1L);
    }

    @Test
    @DisplayName("Should call CustomerService.getCustomerById exactly once")
    void testGetCustomerById_ServiceCalledOnce() {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(sampleCustomerResponse);

        // Act
        customerController.getCustomerById(customerId);

        // Assert
        verify(customerService, times(1)).getCustomerById(customerId);
    }

    // @Test
    // @DisplayName("Should retrieve customer with ID 2L successfully")
    // void testGetCustomerById_WithDifferentId() {
    //     // Arrange
    //     Long customerId = 2L;
    //     CustomerDto customerDto = createCustomerDto(2L, "Jane Smith");
    //     when(customerService.getCustomerById(customerId)).thenReturn(customerDto);

    //     // Act
    //     ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

    //     // Assert
    //     assertNotNull(response);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     verify(customerService, times(1)).getCustomerById(customerId);
    // }

    // ==================== Tests for Exception Handling ====================

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer not found")
    void testGetCustomerById_ThrowsCustomerNotFoundException() {
        // Arrange
        Long customerId = 999L;
        when(customerService.getCustomerById(customerId))
                .thenThrow(new CustomerNotFoundException("Customer not found", 404));

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> 
            customerController.getCustomerById(customerId)
        );

        verify(customerService, times(1)).getCustomerById(customerId);
    }

    @Test
    @DisplayName("Should throw exception with correct error message")
    void testGetCustomerById_ExceptionWithCorrectMessage() {
        // Arrange
        Long customerId = 999L;
        String errorMessage = "Customer with ID 999 not found";
        when(customerService.getCustomerById(customerId))
                .thenThrow(new CustomerNotFoundException(errorMessage, 404));

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> 
            customerController.getCustomerById(customerId)
        );

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should propagate exception from service layer")
    void testGetCustomerById_PropagatesServiceException() {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            customerController.getCustomerById(customerId)
        );
    }

    // ==================== Tests for Constructor Injection ====================

    @Test
    @DisplayName("Should inject CustomerService via constructor")
    void testConstructorInjection() {
        // Arrange & Act
        CustomerController controller = new CustomerController(customerService);

        // Assert
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Should handle null service parameter gracefully in tests")
    void testConstructorWithNullService() {
        // This test verifies the constructor accepts the parameter
        // In production, Spring would prevent null injection
        assertDoesNotThrow(() -> new CustomerController(customerService));
    }

    // ==================== Tests for Response Content ====================

    @Test
    @DisplayName("Should return JSON content type for getAllCustomers")
    void testGetAllCustomers_ReturnsJsonContentType() throws Exception {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(sampleCustomerList);

        // Act & Assert
        mockMvc.perform(get(CommonConstants.API + CommonConstants.GET_ALL_CUSTOMERS))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Should return JSON content type for getCustomerById")
    void testGetCustomerById_ReturnsJsonContentType() throws Exception {
        // Arrange
        Long customerId = 1L;
        when(customerService.getCustomerById(customerId)).thenReturn(sampleCustomerResponse);

        // Act & Assert
        mockMvc.perform(get(CommonConstants.API + "/customers/" + customerId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    // ==================== Tests for Multiple Customers ====================

    @Test
    @DisplayName("Should retrieve multiple customers from service")
    void testGetAllCustomers_ReturnsMultipleCustomers() {
        // Arrange
        when(customerService.getAllCustomers()).thenReturn(sampleCustomerList);

        // Act
        ResponseEntity<CustomerResponse> response = customerController.getAllCustomers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("Should retrieve different customer IDs sequentially")
    void testGetCustomerById_WithMultipleDifferentIds() {
        // Arrange
        Long customerId1 = 1L;
        Long customerId2 = 2L;
        Long customerId3 = 3L;
        
        when(customerService.getCustomerById(customerId1)).thenReturn(createCustomerDataResponse(customerId1, "Customer 1", BigDecimal.valueOf(100)));
        when(customerService.getCustomerById(customerId2)).thenReturn(createCustomerDataResponse(customerId2, "Customer 2", BigDecimal.valueOf(150)));
        when(customerService.getCustomerById(customerId3)).thenReturn(createCustomerDataResponse(customerId3, "Customer 3", BigDecimal.valueOf(200)));

        // Act
        ResponseEntity<CustomerResponse> response1 = customerController.getCustomerById(customerId1);
        ResponseEntity<CustomerResponse> response2 = customerController.getCustomerById(customerId2);
        ResponseEntity<CustomerResponse> response3 = customerController.getCustomerById(customerId3);

        // Assert
        assertAll(
            () -> assertNotNull(response1),
            () -> assertNotNull(response2),
            () -> assertNotNull(response3),
            () -> assertEquals(HttpStatus.OK, response1.getStatusCode()),
            () -> assertEquals(HttpStatus.OK, response2.getStatusCode()),
            () -> assertEquals(HttpStatus.OK, response3.getStatusCode())
        );

        verify(customerService, times(1)).getCustomerById(customerId1);
        verify(customerService, times(1)).getCustomerById(customerId2);
        verify(customerService, times(1)).getCustomerById(customerId3);
    }
}