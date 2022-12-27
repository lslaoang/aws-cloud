package com.aws.basic.configuration.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class AccessGranted {
    @JsonProperty("status")
    private Integer status = 202;

    @JsonProperty("message")
    private String message = "Access granted.";
}
