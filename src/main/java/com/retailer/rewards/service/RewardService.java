package com.retailer.rewards.service;

import com.retailer.rewards.dto.RewardResponseDto;

import java.util.List;

public interface RewardService {

    List<RewardResponseDto> getAllCustomerRewards();

    RewardResponseDto getRewardsByCustomerId(Long customerId);
}