package com.retailer.rewards.dao;

import com.retailer.rewards.dto.TransactionResponseDto;
import com.retailer.rewards.entity.TransactionEntity;

import java.util.List;

public interface TransactionDao {

    List<TransactionResponseDto> getTransactionsByCustomerId(Long customerId);
}