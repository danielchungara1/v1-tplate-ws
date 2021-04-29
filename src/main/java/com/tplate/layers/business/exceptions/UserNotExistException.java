package com.tplate.layers.business.exceptions;

public class UserNotExistException extends BusinessException {

    public UserNotExistException(Long id) {
        super("User not exists. " + id, "The user ID sent is not recognized.");
    }

    public static void throwsException(Long id) throws UserNotExistException {
        throw new UserNotExistException(id);
    }
}
