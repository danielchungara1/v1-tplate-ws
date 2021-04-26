package com.tplate.layers.business.exceptions;

public class UsernameExistException extends BusinessException {

    public UsernameExistException() {
        super("Username exists.", "The username was already taken.");
    }

    public static void throwsException() throws UsernameExistException {
        throw new UsernameExistException();
    }
}

