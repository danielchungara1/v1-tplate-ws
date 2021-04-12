package com.tplate.layers.b.business.exceptions.restexceptions;

public abstract class RestException extends Exception{

    public RestException(String message) {
        super(message);
    }

}
