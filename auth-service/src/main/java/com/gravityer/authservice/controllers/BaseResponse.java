package com.gravityer.authservice.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    public BaseResponse(String message) {
        this.message = message;
        this.data = null;
    }

    public BaseResponse(boolean b, String str) {
        this.success = b;
        this.message = str;
        this.data = null;
    }

    public boolean isSuccess() {
        return success != null && success;
    }
}
