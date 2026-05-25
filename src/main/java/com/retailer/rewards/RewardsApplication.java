package com.retailer.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Entry point for the
 * Retailer Rewards Spring Boot
 * application.
 *
 * This application provides
 * REST APIs to manage customers
 * and calculate reward points
 * based on customer transactions.
 *
 * Reward points are calculated
 * dynamically per month without
 * hardcoding month values.
 */
@SpringBootApplication
public class RewardsApplication {
    /**
     * Main method used to launch
     * the Spring Boot application.
     *
     * @param args
     *         application arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}

}
