package com.retailer.rewards.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object used to
 * send back customer response data
 * to API clients.
 *
 * This DTO is used for creating
 * and updating customer details.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerResponse extends ServiceResponse implements Serializable {
    private List<CustomerDataResponse> customerDataList;
    private CustomerDataResponse customerData;

    public CustomerResponse(List<CustomerDataResponse> customerDataList) {
        this.customerDataList = customerDataList;
    }

    public CustomerResponse() {
    }

    public CustomerResponse(CustomerDataResponse customerData) {
        this.customerData = customerData;
    }

    public List<CustomerDataResponse> getCustomers() {
        return customerDataList;
    }

    public void setCustomers(List<CustomerDataResponse> customerDataList) {
        this.customerDataList = customerDataList;
    }

    public CustomerDataResponse getCustomer() {
        return customerData;
    }

    public void setCustomer(CustomerDataResponse customerData) {
        this.customerData = customerData;
    }

}