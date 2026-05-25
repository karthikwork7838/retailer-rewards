package com.retailer.rewards.mapper;

import com.retailer.rewards.dto.TransactionResponseDto;
import com.retailer.rewards.entity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Mapper class responsible for
 * converting transaction-related
 * data into transaction entities.
 *
 * This class centralizes mapping
 * logic and avoids duplicate
 * object conversion code.
 */
@Component
public class TransactionEntityMapper {

    @Autowired
    private CustomerEntityMapper customerEntityMapper;
    /**
     * Converts transaction entity
     * into transaction response DTO.
     *
     * Used for returning transaction
     * details in API responses.
     *
     * @param transactionEntity
     *         transaction entity
     *
     * @return transaction response DTO
     */
    public TransactionResponseDto mapFrom(TransactionEntity transactionEntity) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setTransactionDate(transactionEntity.getTransactionDate());
        transactionResponseDto.setCustomerResponseDto(customerEntityMapper.mapFrom(transactionEntity.getCustomerEntity()));
        transactionResponseDto.setAmount(transactionEntity.getAmount());
        return transactionResponseDto;
    }
}
