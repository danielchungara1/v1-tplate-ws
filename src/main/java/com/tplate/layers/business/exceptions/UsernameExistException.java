package com.tplate.layers.business.exceptions;

public class UsernameExistException extends BusinessException {

    public UsernameExistException(String username) {
        super("Username exists. " + username, "The username was already taken.");
    }

    public static void throwsException(String username) throws UsernameExistException {
        throw new UsernameExistException(username);
    }
}

