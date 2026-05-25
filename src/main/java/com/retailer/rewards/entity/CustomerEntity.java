package com.retailer.rewards.entity;

import jakarta.persistence.*;
/**
 * Entity representing a customer
 * in the retailer rewards system.
 *
 * A customer can have multiple
 * transactions that are used to
 * calculate reward points.
 */
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
