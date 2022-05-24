package com.example.demo.exception;


import com.example.demo.dto.BaseResponse;

public class ApiException extends AbstractException {

    /**
     * Instantiates a new Notify exception.
     *
     * @param status the status
     */
    public ApiException(Integer status) {
        super(status);
    }

    /**
     * Instantiates a new Notify exception.
     *
     * @param status      the status
     * @param messageArgs the message args
     */
    public ApiException(Integer status, Object... messageArgs) {
        super(status, messageArgs);
    }

    public ApiException(Integer status, String message, Object... messageArgs) {
        super(status, message, messageArgs);
    }

    public BaseResponse getResponseEntity(String message) {
        return new BaseResponse(status, message);
    }
}
