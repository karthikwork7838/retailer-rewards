package com.retailer.rewards.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dao.TransactionDao;
import com.retailer.rewards.dao.impl.CustomerDaoImpl;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.dto.RewardResponseDto;
import com.retailer.rewards.dto.TransactionResponseDto;
import com.retailer.rewards.entity.CustomerEntity;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.mapper.CustomerEntityMapper;
import com.retailer.rewards.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

@ContextConfiguration(classes = {RewardServiceImpl.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class RewardServiceImplTest {
    @MockitoBean
    private CustomerDao customerDao;

    @Autowired
    private RewardServiceImpl rewardServiceImpl;

    @MockitoBean
    private TransactionDao transactionDao;

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards()")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(new CustomerResponseDto());
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
        verify(transactionDao).getTransactionsByCustomerId(isNull());
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards()")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards2() {
        // Arrange
        when(customerDao.getAllCustomers())
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards()")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards3() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());

        CustomerResponseDto customerResponseDto = mock(CustomerResponseDto.class);
        when(customerResponseDto.getId()).thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);

        ArrayList<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        TransactionResponseDto transactionResponseDto =
                new TransactionResponseDto(1L, new CustomerResponseDto(), 100.0d, LocalDate.of(1970, 1, 1));
        transactionResponseDtoList.add(transactionResponseDto);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(transactionResponseDtoList);

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
        verify(transactionDao).getTransactionsByCustomerId(isNull());
        verify(customerResponseDto).getId();
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards()")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards4() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());
        when(customerDao.getCustomerById(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards()")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards5() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());

        CustomerResponseDto customerResponseDto = mock(CustomerResponseDto.class);
        when(customerResponseDto.getId()).thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);

        ArrayList<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        TransactionResponseDto transactionResponseDto =
                new TransactionResponseDto(1L, new CustomerResponseDto(), 10.0d, LocalDate.of(1970, 1, 1));
        transactionResponseDtoList.add(transactionResponseDto);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(transactionResponseDtoList);

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
        verify(transactionDao).getTransactionsByCustomerId(isNull());
        verify(customerResponseDto).getId();
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <ul>
     *   <li>Given {@link CustomerDao} {@link CustomerDao#getCustomerById(Long)} return {@code null}.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName(
            "Test getAllCustomerRewards(); given CustomerDao getCustomerById(Long) return 'null'")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards_givenCustomerDaoGetCustomerByIdReturnNull() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(null);
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <ul>
     *   <li>Given {@link TransactionDao}.
     *   <li>Then return Empty.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards(); given TransactionDao; then return Empty")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards_givenTransactionDao_thenReturnEmpty() {
        // Arrange
        when(customerDao.getAllCustomers()).thenReturn(new ArrayList<>());

        // Act
        List<RewardResponseDto> actualAllCustomerRewards = rewardServiceImpl.getAllCustomerRewards();

        // Assert
        verify(customerDao).getAllCustomers();
        assertTrue(actualAllCustomerRewards.isEmpty());
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <ul>
     *   <li>Then calls {@link CustomerResponseDto#getId()}.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards(); then calls getId()")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards_thenCallsGetId() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());

        CustomerResponseDto customerResponseDto = mock(CustomerResponseDto.class);
        when(customerResponseDto.getId()).thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.getAllCustomerRewards());
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
        verify(transactionDao).getTransactionsByCustomerId(isNull());
        verify(customerResponseDto).getId();
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <ul>
     *   <li>Then return first CustomerId is {@code null}.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards(); then return first CustomerId is 'null'")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards_thenReturnFirstCustomerIdIsNull() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(new CustomerResponseDto());
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        List<RewardResponseDto> actualAllCustomerRewards = rewardServiceImpl.getAllCustomerRewards();

        // Assert
        verify(customerDao).getAllCustomers();
        verify(customerDao).getCustomerById(isNull());
        verify(transactionDao).getTransactionsByCustomerId(isNull());
        assertEquals(1, actualAllCustomerRewards.size());
        RewardResponseDto getResult = actualAllCustomerRewards.get(0);
        assertNull(getResult.getCustomerId());
        assertNull(getResult.getCustomerName());
        assertEquals(0, getResult.getTotalRewards().intValue());
        assertTrue(getResult.getMonthlyRewards().isEmpty());
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <ul>
     *   <li>Then return first MonthlyRewards {@code JANUARY} intValue is zero.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName(
            "Test getAllCustomerRewards(); then return first MonthlyRewards 'JANUARY' intValue is zero")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards_thenReturnFirstMonthlyRewardsJanuaryIntValueIsZero() {
        // Arrange
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("Name");

        ArrayList<CustomerEntity> customerEntityList = new ArrayList<>();
        customerEntityList.add(customerEntity);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setName("Name");
        Optional<CustomerEntity> ofResult = Optional.of(customerEntity2);

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(customerRepository.findAll()).thenReturn(customerEntityList);
        CustomerDaoImpl customerDao =
                new CustomerDaoImpl(customerRepository, new CustomerEntityMapper());

        TransactionResponseDto transactionResponseDto =
                new TransactionResponseDto(1L, new CustomerResponseDto(), 10.0d, LocalDate.of(1970, 1, 1));
        transactionResponseDto.setTransactionDate(LocalDate.now());

        ArrayList<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        TransactionResponseDto transactionResponseDto2 =
                new TransactionResponseDto(1L, new CustomerResponseDto(), 10.0d, LocalDate.of(1970, 1, 1));
        transactionResponseDtoList.add(transactionResponseDto2);
        transactionResponseDtoList.add(transactionResponseDto);

        TransactionDao transactionDao = mock(TransactionDao.class);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(transactionResponseDtoList);

        RewardServiceImpl rewardServiceImpl = new RewardServiceImpl(customerDao, transactionDao);

        // Act
        List<RewardResponseDto> actualAllCustomerRewards = rewardServiceImpl.getAllCustomerRewards();

        // Assert
        verify(transactionDao).getTransactionsByCustomerId(isNull());
        verify(customerRepository).findById(isNull());
        verify(customerRepository).findAll();
        assertEquals(1, actualAllCustomerRewards.size());
        RewardResponseDto getResult = actualAllCustomerRewards.get(0);
        assertEquals(0, getResult.getTotalRewards().intValue());
        assertEquals(0, getResult.getMonthlyRewards().get("JANUARY").intValue());
    }

    /**
     * Test {@link RewardServiceImpl#getAllCustomerRewards()}.
     *
     * <ul>
     *   <li>Then return size is two.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getAllCustomerRewards()}
     */
    @Test
    @DisplayName("Test getAllCustomerRewards(); then return size is two")
    @MethodsUnderTest({"List RewardServiceImpl.getAllCustomerRewards()"})
    void testGetAllCustomerRewards_thenReturnSizeIsTwo() {
        // Arrange
        ArrayList<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();
        customerResponseDtoList.add(new CustomerResponseDto());
        customerResponseDtoList.add(new CustomerResponseDto());
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(new CustomerResponseDto());
        when(customerDao.getAllCustomers()).thenReturn(customerResponseDtoList);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        List<RewardResponseDto> actualAllCustomerRewards = rewardServiceImpl.getAllCustomerRewards();

        // Assert
        verify(customerDao).getAllCustomers();
        verify(customerDao, atLeast(1)).getCustomerById(isNull());
        verify(transactionDao, atLeast(1)).getTransactionsByCustomerId(isNull());
        assertEquals(2, actualAllCustomerRewards.size());
        RewardResponseDto getResult = actualAllCustomerRewards.get(1);
        assertNull(getResult.getCustomerId());
        assertNull(getResult.getCustomerName());
        assertEquals(0, getResult.getTotalRewards().intValue());
        assertTrue(getResult.getMonthlyRewards().isEmpty());
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName("Test getRewardsByCustomerId(Long)")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId() {
        // Arrange
        when(customerDao.getCustomerById(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> rewardServiceImpl.getRewardsByCustomerId(1L));
        verify(customerDao).getCustomerById(1L);
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName("Test getRewardsByCustomerId(Long)")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId2() {
        // Arrange
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(new CustomerResponseDto());
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> rewardServiceImpl.getRewardsByCustomerId(1L));
        verify(customerDao).getCustomerById(1L);
        verify(transactionDao).getTransactionsByCustomerId(1L);
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName("Test getRewardsByCustomerId(Long)")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId3() {
        // Arrange
        CustomerResponseDto customerResponseDto = mock(CustomerResponseDto.class);
        when(customerResponseDto.getId()).thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);

        ArrayList<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        TransactionResponseDto transactionResponseDto =
                new TransactionResponseDto(1L, new CustomerResponseDto(), 10.0d, LocalDate.of(1970, 1, 1));
        transactionResponseDtoList.add(transactionResponseDto);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(transactionResponseDtoList);

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> rewardServiceImpl.getRewardsByCustomerId(1L));
        verify(customerDao).getCustomerById(1L);
        verify(transactionDao).getTransactionsByCustomerId(1L);
        verify(customerResponseDto).getId();
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName("Test getRewardsByCustomerId(Long)")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId4() {
        // Arrange
        CustomerResponseDto customerResponseDto = mock(CustomerResponseDto.class);
        when(customerResponseDto.getId()).thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);

        ArrayList<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        TransactionResponseDto transactionResponseDto =
                new TransactionResponseDto(1L, new CustomerResponseDto(), 100.0d, LocalDate.of(1970, 1, 1));
        transactionResponseDtoList.add(transactionResponseDto);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(transactionResponseDtoList);

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> rewardServiceImpl.getRewardsByCustomerId(1L));
        verify(customerDao).getCustomerById(1L);
        verify(transactionDao).getTransactionsByCustomerId(1L);
        verify(customerResponseDto).getId();
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <ul>
     *   <li>Given {@link CustomerDao} {@link CustomerDao#getCustomerById(Long)} return {@code null}.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName(
            "Test getRewardsByCustomerId(Long); given CustomerDao getCustomerById(Long) return 'null'")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId_givenCustomerDaoGetCustomerByIdReturnNull() {
        // Arrange
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(null);

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> rewardServiceImpl.getRewardsByCustomerId(1L));
        verify(customerDao).getCustomerById(1L);
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <ul>
     *   <li>Then calls {@link CustomerResponseDto#getId()}.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName("Test getRewardsByCustomerId(Long); then calls getId()")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId_thenCallsGetId() {
        // Arrange
        CustomerResponseDto customerResponseDto = mock(CustomerResponseDto.class);
        when(customerResponseDto.getId()).thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(customerResponseDto);
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(
                CustomerNotFoundException.class, () -> rewardServiceImpl.getRewardsByCustomerId(1L));
        verify(customerDao).getCustomerById(1L);
        verify(transactionDao).getTransactionsByCustomerId(1L);
        verify(customerResponseDto).getId();
    }

    /**
     * Test {@link RewardServiceImpl#getRewardsByCustomerId(Long)}.
     *
     * <ul>
     *   <li>Then return CustomerId is {@code null}.
     * </ul>
     *
     * <p>Method under test: {@link RewardServiceImpl#getRewardsByCustomerId(Long)}
     */
    @Test
    @DisplayName("Test getRewardsByCustomerId(Long); then return CustomerId is 'null'")
    @MethodsUnderTest({"RewardResponseDto RewardServiceImpl.getRewardsByCustomerId(Long)"})
    void testGetRewardsByCustomerId_thenReturnCustomerIdIsNull() {
        // Arrange
        when(customerDao.getCustomerById(Mockito.<Long>any())).thenReturn(new CustomerResponseDto());
        when(transactionDao.getTransactionsByCustomerId(Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        // Act
        RewardResponseDto actualRewardsByCustomerId = rewardServiceImpl.getRewardsByCustomerId(1L);

        // Assert
        verify(customerDao).getCustomerById(1L);
        verify(transactionDao).getTransactionsByCustomerId(1L);
        assertNull(actualRewardsByCustomerId.getCustomerId());
        assertNull(actualRewardsByCustomerId.getCustomerName());
        assertEquals(0, actualRewardsByCustomerId.getTotalRewards().intValue());
        assertTrue(actualRewardsByCustomerId.getMonthlyRewards().isEmpty());
    }
}
