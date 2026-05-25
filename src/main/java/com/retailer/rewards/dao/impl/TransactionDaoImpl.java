package com.retailer.rewards.dao.impl;

import com.retailer.rewards.dao.TransactionDao;
import com.retailer.rewards.dto.TransactionResponseDto;
import com.retailer.rewards.entity.TransactionEntity;
import com.retailer.rewards.mapper.TransactionEntityMapper;
import com.retailer.rewards.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionDaoImpl implements TransactionDao {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionEntityMapper transactionEntityMapper;


    public TransactionDaoImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionResponseDto> getTransactionsByCustomerId(Long customerId) {
        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = transactionRepository.findByCustomerId(customerId);
        if(!ObjectUtils.isEmpty(transactionEntityList)){
            transactionResponseDtoList =  transactionEntityList.stream().map(transactionEntity->transactionEntityMapper.mapFrom(transactionEntity)).toList();
        }
        return transactionResponseDtoList;
    }
}