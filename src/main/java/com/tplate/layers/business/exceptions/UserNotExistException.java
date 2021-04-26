package com.tplate.layers.business.exceptions;

public class UserNotExistException extends BusinessException {

    public UserNotExistException() {
        super("User not exists.", "The user ID sent is not recognized.");
    }

    public static void throwsException() throws UserNotExistException {
        throw new UserNotExistException();
    }
}
