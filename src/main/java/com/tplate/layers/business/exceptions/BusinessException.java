package com.tplate.layers.business.exceptions;

import lombok.Data;

@Data
public  class BusinessException extends Exception{

    private String details;

    public BusinessException(String message, String details) {
        super(message);
        this.details  = details;
    }

}
