package com.retailer.rewards.repository;

import com.retailer.rewards.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository
        extends JpaRepository<TransactionEntity, Long> {
    @Query(value = "SELECT trs FROM TransactionEntity trs WHERE trs.customerEntity.id =:customerId")
    List<TransactionEntity> findByCustomerId(@Param("customerId")
            Long customerId);
}
