package com.retailer.rewards.controller;

import com.retailer.rewards.constants.CommonConstants;
import com.retailer.rewards.dto.RewardResponseDto;
import com.retailer.rewards.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for reward-related APIs.
 *
 * This controller provides endpoints
 * to retrieve reward points earned by
 * customers based on transaction history.
 *
 * Rewards are calculated dynamically
 * per customer and grouped by month
 * without hardcoding month values.
 */
@RestController
@RequestMapping(value = CommonConstants.API, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = CommonConstants.CROSS_ORIGIN)
public class RewardController {

    @Autowired
    private RewardService rewardService;

    public RewardController(RewardService rewardService) {

        this.rewardService = rewardService;
    }
    /**
     * Retrieves reward details for
     * all customers.
     *
     * Returns monthly rewards along
     * with total reward points for
     * each customer.
     *
     * @return list of customer rewards
     */
    @GetMapping(value = CommonConstants.GET_ALL_REWARDS,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RewardResponseDto> getAllRewards() {
        return rewardService.getAllCustomerRewards();
    }
    /**
     * Retrieves reward details for
     * a specific customer.
     *
     * Reward points are grouped
     * dynamically by month based
     * on transaction date.
     *
     * @param customerId
     *         unique customer identifier
     *
     * @return reward details for
     *         the customer
     */
    @GetMapping(value = CommonConstants.REWARD_BY_CUSTOMER_ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public RewardResponseDto getRewardsByCustomerId(@PathVariable Long customerId) {

        return rewardService.getRewardsByCustomerId(customerId);
    }
}