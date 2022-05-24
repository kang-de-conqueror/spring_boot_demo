package com.example.demo.exception;

import com.example.demo.constant.Status;
import com.example.demo.dto.response.BaseResponse;

/**
 * The type Abstract payment exception.
 */
public abstract class AbstractException extends RuntimeException {
    private static final long serialVersionUID = -6302032012948471046L;
    /**
     * The Message args.
     */
    protected Object[] messageArgs;
    /**
     * The Status.
     */
    protected Integer status;

    /**
     * Instantiates a new Payment exception.
     *
     * @param status the status
     */
    protected AbstractException(Integer status) {
        super(Status.getDesc(status));
        this.status = status;
        this.messageArgs = new Object[0];
    }

    /**
     * Instantiates a new Payment exception.
     *
     * @param status      the status
     * @param messageArgs the message args
     */
    protected AbstractException(Integer status, Object... messageArgs) {
        super(Status.getDesc(status));
        this.status = status;
        this.messageArgs = messageArgs;
    }

    protected AbstractException(Integer status, String message, Object... messageArgs) {
        super(message);
        this.status = status;
        this.messageArgs = messageArgs;
    }

    /**
     * Gets response entity.
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the response entity
     */
    public abstract <T extends BaseResponse> T getResponseEntity(String message);

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get message args object [ ].
     *
     * @return the object [ ]
     */
    public Object[] getMessageArgs() {
        return messageArgs;
    }
}
