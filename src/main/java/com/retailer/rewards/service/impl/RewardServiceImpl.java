package com.retailer.rewards.service.impl;

import com.retailer.rewards.dao.CustomerDao;
import com.retailer.rewards.dao.TransactionDao;
import com.retailer.rewards.dto.CustomerResponseDto;
import com.retailer.rewards.dto.RewardResponseDto;
import com.retailer.rewards.dto.TransactionResponseDto;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation responsible
 * for reward calculation operations.
 * <p>
 * This class calculates reward points
 * earned by customers based on their
 * transactions and groups rewards
 * dynamically by month without
 * hardcoding month values.
 * <p>
 * Reward rules:
 * - 2 points for every dollar spent
 * above 100 dollars
 * - 1 point for every dollar spent
 * between 50 and 100 dollars
 */
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TransactionDao transactionDao;

    /**
     * Constructs
     * RewardServiceImpl with
     * required dependencies.
     *
     * @param customerDao    customer DAO
     * @param transactionDao transaction DAO
     */
    public RewardServiceImpl(CustomerDao customerDao, TransactionDao transactionDao) {

        this.customerDao = customerDao;

        this.transactionDao = transactionDao;
    }

    /**
     * Retrieves rewards for
     * all customers.
     * <p>
     * Reward points are grouped
     * dynamically by transaction
     * month and include total
     * reward points.
     *
     * @return list of customer
     * reward details
     */
    @Override
    public List<RewardResponseDto> getAllCustomerRewards() {

        return customerDao.getAllCustomers().stream().map(customer -> getRewardsByCustomerId(customer.getId())).toList();
    }

    /**
     * Retrieves reward details
     * for a specific customer.
     * <p>
     * Reward points are grouped
     * dynamically by month
     * derived from transaction
     * dates.
     * <p>
     * Throws exception if
     * customer does not exist.
     *
     * @param customerId unique customer id
     * @return customer reward details
     */
    @Override
    public RewardResponseDto getRewardsByCustomerId(Long customerId) {

        CustomerResponseDto customerResponseDto = customerDao.getCustomerById(customerId);

        if (ObjectUtils.isEmpty(customerResponseDto)) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }

        List<TransactionResponseDto> transactionResponseDtoList = transactionDao.getTransactionsByCustomerId(customerId);

        Map<String, Integer> monthlyRewards = transactionResponseDtoList.stream().sorted(Comparator.comparing(TransactionResponseDto::getTransactionDate)).collect(Collectors.groupingBy(transactionResponseDto -> transactionResponseDto.getTransactionDate().getMonth(), LinkedHashMap::new, Collectors.summingInt(transactionResponseDto -> calculateRewardPoints(transactionResponseDto.getAmount())))).entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

        return new RewardResponseDto(customerResponseDto.getId(), customerResponseDto.getName(), monthlyRewards, totalRewards);
    }

    private int calculateRewardPoints(Double amount) {
        int rewardPoints = 0;
        if (amount > 100) {
            rewardPoints += (int) ((amount - 100) * 2);
            amount = 100.0;
        }
        if (amount > 50) {
            rewardPoints += (int) (amount - 50);
        }
        return rewardPoints;
    }
}