package com.tplate.layers.b.business.exceptions;

public class UserNotFoundException extends RestException {

    public UserNotFoundException() {
        super("User not exists.", "The user ID sent is not recognized.");
    }
}
