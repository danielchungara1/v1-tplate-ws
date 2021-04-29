package com.tplate.layers.business.exceptions;

public class UsernameNotExistException extends BusinessException {

    public UsernameNotExistException() {
        super("Username not exists.", "The username is not recognized.");
    }

    public static void throwsException() throws UsernameNotExistException {
        throw new UsernameNotExistException();
    }
}

