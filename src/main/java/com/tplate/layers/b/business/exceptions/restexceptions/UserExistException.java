package com.tplate.layers.b.business.exceptions.restexceptions;

public class UserExistException extends RestException {

    public UserExistException() {
        super("Username exists.");
    }


}
