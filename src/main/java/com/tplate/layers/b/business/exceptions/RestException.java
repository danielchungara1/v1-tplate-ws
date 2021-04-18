package com.tplate.layers.b.business.exceptions;

import lombok.Data;

@Data
public  class RestException extends Exception{

    private String details;

    public RestException(String message, String details) {
        super(message);
        this.details  = details;
    }

}
