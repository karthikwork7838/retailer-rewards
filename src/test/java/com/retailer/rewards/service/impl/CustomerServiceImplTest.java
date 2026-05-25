package com.retailer.rewards.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dto.CustomerRequestDto;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.exception.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerServiceImpl.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @MockitoBean
    private CustomerDao customerDao;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    /**
     * Test {@link CustomerServiceImpl#createCustomer(CustomerRequestDto)}.
     *
     * <ul>
     *   <li>Then return {@link CustomerResponseDto#CustomerResponseDto()}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#createCustomer(CustomerRequestDto)}
     */
    @Test
    @DisplayName("Test createCustomer(CustomerRequestDto); then return CustomerResponseDto()")
    @MethodsUnderTest({"CustomerResponseDto CustomerServiceImpl.createCustomer(CustomerRequestDto)"})
    void testCreateCustomer_thenReturnCustomerResponseDto() {
        // Arrange
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        when(customerDao.saveCustomer(Mockito.<CustomerRequestDto>any()))
                .thenReturn(customerResponseDto);

        CustomerRequestDto requestDto = new CustomerRequestDto();
        requestDto.setName("Name");

        // Act
        CustomerResponseDto actualCreateCustomerResult = customerServiceImpl.createCustomer(requestDto);

        // Assert
        verify(customerDao).saveCustomer(isA(CustomerRequestDto.class));
        assertSame(customerResponseDto, actualCreateCustomerResult);
    }

    /**
     * Test {@link CustomerServiceImpl#createCustomer(CustomerRequestDto)}.
     *
     * <ul>
     *   <li>Then throw {@link CustomerNotFoundException}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#createCustomer(CustomerRequestDto)}
     */
    @Test
    @DisplayName("Test createCustomer(CustomerRequestDto); then throw CustomerNotFoundException")
    @MethodsUnderTest({"CustomerResponseDto CustomerServiceImpl.createCustomer(CustomerRequestDto)"})
    void testCreateCustomer_thenThrowCustomerNotFoundException() {
        // Arrange
        when(customerDao.saveCustomer(Mockito.<CustomerRequestDto>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        CustomerRequestDto requestDto = new CustomerRequestDto();
        requestDto.setName("Name");

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> customerServiceImpl.createCustomer(requestDto));
        verify(customerDao).saveCustomer(isA(CustomerRequestDto.class));
    }

    /**
     * Test {@link CustomerServiceImpl#getAllCustomers()}.
     *
     * <ul>
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#getAllCustomers()}
     */
    @Test
    @DisplayName("Test getAllCustomers(); then return Empty")
    @MethodsUnderTest({"List CustomerServiceImpl.getAllCustomers()"})
    void testGetAllCustomers_thenReturnEmpty() {
        // Arrange
        when(customerDao.getAllCustomers()).thenReturn(new ArrayList<>());

        // Act
        List<CustomerResponseDto> actualAllCustomers = customerServiceImpl.getAllCustomers();

        // Assert
        verify(customerDao).getAllCustomers();
        assertTrue(actualAllCustomers.isEmpty());
    }

    /**
     * Test {@link CustomerServiceImpl#getAllCustomers()}.
     *
     * <ul>
     *   <li>Then throw {@link CustomerNotFoundException}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#getAllCustomers()}
     */
    @Test
    @DisplayName("Test getAllCustomers(); then throw CustomerNotFoundException")
    @MethodsUnderTest({"List CustomerServiceImpl.getAllCustomers()"})
    void testGetAllCustomers_thenThrowCustomerNotFoundException() {
        // Arrange
        when(customerDao.getAllCustomers())
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.getAllCustomers());
        verify(customerDao).getAllCustomers();
    }

    /**
     * Test {@link CustomerServiceImpl#getCustomerById(Long)}.
     *
     * <p>Method under test: {@link CustomerServiceImpl#getCustomerById(Long)}
     */
    @Test
    @DisplayName("Test getCustomerById(Long)")
    @MethodsUnderTest({"CustomerResponseDto CustomerServiceImpl.getCustomerById(Long)"})
    void testGetCustomerById() {
        // Arrange
        when(customerDao.getCustomerById(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.getCustomerById(1L));
        verify(customerDao).getCustomerById(1L);
    }

    /**
     * Test {@link CustomerServiceImpl#getCustomerById(Long)}.
     *
     * <ul>
     *   <li>Given {@link CustomerDao} {@link CustomerDao#getCustomerById(Long)} return {@code null}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#getCustomerById(Long)}
     */
    @Test
    @DisplayName("Test getCustomerById(Long); given CustomerDao getCustomerById(Long) return 'null'")
    @MethodsUnderTest({"CustomerResponseDto CustomerServiceImpl.getCustomerById(Long)"})
    void testGetCustomerById_givenCustomerDaoGetCustomerByIdReturnNull() {
        // Arrange
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(null);

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.getCustomerById(1L));
        verify(customerDao).getCustomerById(1L);
    }

    /**
     * Test {@link CustomerServiceImpl#getCustomerById(Long)}.
     *
     * <ul>
     *   <li>Then return {@link CustomerResponseDto#CustomerResponseDto()}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#getCustomerById(Long)}
     */
    @Test
    @DisplayName("Test getCustomerById(Long); then return CustomerResponseDto()")
    @MethodsUnderTest({"CustomerResponseDto CustomerServiceImpl.getCustomerById(Long)"})
    void testGetCustomerById_thenReturnCustomerResponseDto() {
        // Arrange
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);

        // Act
        CustomerResponseDto actualCustomerById = customerServiceImpl.getCustomerById(1L);

        // Assert
        verify(customerDao).getCustomerById(1L);
        assertSame(customerResponseDto, actualCustomerById);
    }

    /**
     * Test {@link CustomerServiceImpl#updateCustomer(Long, CustomerRequestDto)}.
     *
     * <ul>
     *   <li>Then return {@link CustomerResponseDto#CustomerResponseDto()}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#updateCustomer(Long, CustomerRequestDto)}
     */
    @Test
    @DisplayName("Test updateCustomer(Long, CustomerRequestDto); then return CustomerResponseDto()")
    @MethodsUnderTest({
            "CustomerResponseDto CustomerServiceImpl.updateCustomer(Long, CustomerRequestDto)"
    })
    void testUpdateCustomer_thenReturnCustomerResponseDto() {
        // Arrange
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        when(customerDao.updateCustomerById(Mockito.<Long>any(), Mockito.<CustomerRequestDto>any()))
                .thenReturn(customerResponseDto);

        CustomerRequestDto requestDto = new CustomerRequestDto();
        requestDto.setName("Name");

        // Act
        CustomerResponseDto actualUpdateCustomerResult =
                customerServiceImpl.updateCustomer(1L, requestDto);

        // Assert
        verify(customerDao).updateCustomerById(eq(1L), isA(CustomerRequestDto.class));
        assertSame(customerResponseDto, actualUpdateCustomerResult);
    }

    /**
     * Test {@link CustomerServiceImpl#updateCustomer(Long, CustomerRequestDto)}.
     *
     * <ul>
     *   <li>Then throw {@link CustomerNotFoundException}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#updateCustomer(Long, CustomerRequestDto)}
     */
    @Test
    @DisplayName(
            "Test updateCustomer(Long, CustomerRequestDto); then throw CustomerNotFoundException")
    @MethodsUnderTest({
            "CustomerResponseDto CustomerServiceImpl.updateCustomer(Long, CustomerRequestDto)"
    })
    void testUpdateCustomer_thenThrowCustomerNotFoundException() {
        // Arrange
        when(customerDao.updateCustomerById(Mockito.<Long>any(), Mockito.<CustomerRequestDto>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        CustomerRequestDto requestDto = new CustomerRequestDto();
        requestDto.setName("Name");

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> customerServiceImpl.updateCustomer(1L, requestDto));
        verify(customerDao).updateCustomerById(eq(1L), isA(CustomerRequestDto.class));
    }

    /**
     * Test {@link CustomerServiceImpl#deleteCustomer(Long)}.
     *
     * <ul>
     *   <li>Given {@link CustomerDao} {@link CustomerDao#deleteCustomer(Long)} does nothing.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#deleteCustomer(Long)}
     */
    @Test
    @DisplayName("Test deleteCustomer(Long); given CustomerDao deleteCustomer(Long) does nothing")
    @MethodsUnderTest({"void CustomerServiceImpl.deleteCustomer(Long)"})
    void testDeleteCustomer_givenCustomerDaoDeleteCustomerDoesNothing() {
        // Arrange
        doNothing().when(customerDao).deleteCustomer(Mockito.<Long>any());

        // Act
        customerServiceImpl.deleteCustomer(1L);

        // Assert
        verify(customerDao).deleteCustomer(1L);
    }

    /**
     * Test {@link CustomerServiceImpl#deleteCustomer(Long)}.
     *
     * <ul>
     *   <li>Then throw {@link CustomerNotFoundException}.
     * </ul>
     *
     * <p>Method under test: {@link CustomerServiceImpl#deleteCustomer(Long)}
     */
    @Test
    @DisplayName("Test deleteCustomer(Long); then throw CustomerNotFoundException")
    @MethodsUnderTest({"void CustomerServiceImpl.deleteCustomer(Long)"})
    void testDeleteCustomer_thenThrowCustomerNotFoundException() {
        // Arrange
        doThrow(new CustomerNotFoundException("An error occurred"))
                .when(customerDao)
                .deleteCustomer(Mockito.<Long>any());

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> customerServiceImpl.deleteCustomer(1L));
        verify(customerDao).deleteCustomer(1L);
    }
}
