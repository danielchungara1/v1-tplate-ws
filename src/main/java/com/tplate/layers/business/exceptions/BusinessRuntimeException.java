package com.tplate.layers.business.exceptions;

import lombok.Data;

@Data
public  class BusinessRuntimeException extends RuntimeException{

    private String details;

    public BusinessRuntimeException(String message, String details) {
        super(message);
        this.details  = details;
    }

}
