package com.retailer.rewards.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.retailer.rewards.dao.impl.CustomerDaoImpl;
import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.entity.CustomerEntity;
import com.retailer.rewards.exception.GlobalExceptionHandler;
import com.retailer.rewards.mapper.CustomerEntityMapper;
import com.retailer.rewards.repository.CustomerRepository;
import com.retailer.rewards.service.CustomerService;
import com.retailer.rewards.service.impl.CustomerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class, GlobalExceptionHandler.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Test {@link CustomerController#createCustomer(CustomerRequestDto)}.
     *
     * <p>Method under test: {@link CustomerController#createCustomer(CustomerRequestDto)}
     */
    @Test
    @DisplayName("Test createCustomer(CustomerRequestDto)")
    @Disabled("TODO: Complete this test")
    @MethodsUnderTest({"ResponseEntity CustomerController.createCustomer(CustomerRequestDto)"})
    void testCreateCustomer() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.util.PlaceholderResolutionException: Could not resolve placeholder
        // 'cross.origin' in value "${cross.origin}"
        //       at java.base/java.util.LinkedHashMap.forEach(LinkedHashMap.java:721)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setName("Name");

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/api/v1/saveCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                JsonMapper.builder()
                                        .findAndAddModules()
                                        .build()
                                        .writeValueAsString(customerRequestDto));

        // Act
        MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder);
    }

    /**
     * Test {@link CustomerController#createCustomer(CustomerRequestDto)}.
     *
     * <ul>
     *   <li>Then StatusCode return {@link HttpStatus}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerController#createCustomer(CustomerRequestDto)}
     */
    @Test
    @DisplayName("Test createCustomer(CustomerRequestDto); then StatusCode return HttpStatus")
    @MethodsUnderTest({"ResponseEntity CustomerController.createCustomer(CustomerRequestDto)"})
    void testCreateCustomer_thenStatusCodeReturnHttpStatus() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("Name");

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save(Mockito.<CustomerEntity>any())).thenReturn(customerEntity);
        CustomerDaoImpl customerDao =
                new CustomerDaoImpl(customerRepository, new CustomerEntityMapper());
        CustomerController customerController =
                new CustomerController(new CustomerServiceImpl(customerDao));

        CustomerRequestDto requestDto = new CustomerRequestDto();
        requestDto.setName("Name");

        // Act
        ResponseEntity<CustomerResponseDto> actualCreateCustomerResult =
                customerController.createCustomer(requestDto);

        // Assert
        verify(customerRepository).save(isA(CustomerEntity.class));
        HttpStatusCode statusCode = actualCreateCustomerResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        CustomerResponseDto body = actualCreateCustomerResult.getBody();
        assertEquals("Name", body.getName());
        assertNull(body.getId());
        assertEquals(201, actualCreateCustomerResult.getStatusCodeValue());
        assertEquals(HttpStatus.CREATED, statusCode);
        assertTrue(actualCreateCustomerResult.hasBody());
        assertTrue(actualCreateCustomerResult.getHeaders().isEmpty());
    }

    /**
     * Test {@link CustomerController#getAllCustomers()}.
     *
     * <p>Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    @DisplayName("Test getAllCustomers()")
    @Disabled("TODO: Complete this test")
    @MethodsUnderTest({"List CustomerController.getAllCustomers()"})
    void testGetAllCustomers() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.util.PlaceholderResolutionException: Could not resolve placeholder
        // 'cross.origin' in value "${cross.origin}"
        //       at java.base/java.util.LinkedHashMap.forEach(LinkedHashMap.java:721)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/allCustomers");

        // Act
        MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder);
    }

    /**
     * Test {@link CustomerController#getAllCustomers()}.
     *
     * <ul>
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link CustomerController#getAllCustomers()}
     */
    @Test
    @DisplayName("Test getAllCustomers(); then return Empty")
    @MethodsUnderTest({"List CustomerController.getAllCustomers()"})
    void testGetAllCustomers_thenReturnEmpty() {

        // Arrange
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        CustomerDaoImpl customerDao =
                new CustomerDaoImpl(customerRepository, new CustomerEntityMapper());

        // Act
        List<CustomerResponseDto> actualAllCustomers =
                new CustomerController(new CustomerServiceImpl(customerDao)).getAllCustomers();

        // Assert
        verify(customerRepository).findAll();
        assertTrue(actualAllCustomers.isEmpty());
    }

    /**
     * Test {@link CustomerController#deleteCustomer(Long)}.
     *
     * <p>Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    @DisplayName("Test deleteCustomer(Long)")
    @Disabled("TODO: Complete this test")
    @MethodsUnderTest({"ResponseEntity CustomerController.deleteCustomer(Long)"})
    void testDeleteCustomer() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.delete("/api/v1/customers/{id}", 1L);

        // Act
        MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder);
    }

    /**
     * Test {@link CustomerController#deleteCustomer(Long)}.
     *
     * <ul>
     *   <li>Given {@link CustomerEntity} (default constructor) Name is {@code Name}.
     *   <li>Then StatusCode return {@link HttpStatus}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    @DisplayName(
            "Test deleteCustomer(Long); given CustomerEntity (default constructor) Name is 'Name'; then StatusCode return HttpStatus")
    @MethodsUnderTest({"ResponseEntity CustomerController.deleteCustomer(Long)"})
    void testDeleteCustomer_givenCustomerEntityNameIsName_thenStatusCodeReturnHttpStatus() {

        // Arrange
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("Name");
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity);

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        doNothing().when(customerRepository).delete(Mockito.<CustomerEntity>any());
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDaoImpl customerDao =
                new CustomerDaoImpl(customerRepository, new CustomerEntityMapper());

        // Act
        ResponseEntity<Void> actualDeleteCustomerResult =
                new CustomerController(new CustomerServiceImpl(customerDao)).deleteCustomer(1L);

        // Assert
        verify(customerRepository).delete(isA(CustomerEntity.class));
        verify(customerRepository).findById(1L);
        HttpStatusCode statusCode = actualDeleteCustomerResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertNull(actualDeleteCustomerResult.getBody());
        assertEquals(204, actualDeleteCustomerResult.getStatusCodeValue());
        assertEquals(HttpStatus.NO_CONTENT, statusCode);
        assertFalse(actualDeleteCustomerResult.hasBody());
        assertTrue(actualDeleteCustomerResult.getHeaders().isEmpty());
    }
}
