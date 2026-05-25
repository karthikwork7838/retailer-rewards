package com.retailer.rewards.repository;

import com.retailer.rewards.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends JpaRepository<CustomerEntity, Long> {
}
