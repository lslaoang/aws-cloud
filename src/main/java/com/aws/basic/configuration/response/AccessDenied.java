package com.aws.basic.configuration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;


@Validated
public class AccessDenied {
    @JsonProperty("status")
    private Integer status = 401;

    @JsonProperty("message")
    private String message = "Access denied.";

    public AccessDenied message(String message) {
        this.message = message;
        return this;
    }
}
