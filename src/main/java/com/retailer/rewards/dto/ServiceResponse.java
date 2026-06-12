package com.retailer.rewards.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Model class used for API response
 */
public class ServiceResponse {
    @JsonProperty(index = 0)
    private Integer status;
    @JsonProperty(index = 1)
    private String title;
    @JsonProperty(index = 2)
    private String correlationId;
    public ServiceResponse(HttpStatus status){
        super();
        this.status = status.value();
    }

    public ServiceResponse() {
    }
    public ResponseEntity<Object>build(){
        return new ResponseEntity<>(this,HttpStatus.valueOf(status));
    }
    public ResponseEntity<Object>build(String title,Integer status){
        this.title=title;
        this.status=status;
        return new ResponseEntity<>(this,HttpStatus.valueOf(status));
    }
    public <T>ResponseEntity<T>build(String title,Integer status, T ResponseBody){
        this.title=title;
        this.status=status;
        return new ResponseEntity<>(ResponseBody,HttpStatus.valueOf(status));
    }

}
