package com.tplate.layers.b.business.exceptions.restexceptions;

public class UserNotFoundException extends RestException {

    public UserNotFoundException() {
        super("User not fournd.");
    }

}
