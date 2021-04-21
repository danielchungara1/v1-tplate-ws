package com.tplate.layers.b.business.exceptions;

public class UserNotExistException extends BusinessException {

    public UserNotExistException() {
        super("User not exists.", "The user ID sent is not recognized.");
    }
}
