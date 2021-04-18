package com.tplate.layers.b.business.exceptions;

public class UsernameExistException extends RestException {

    public UsernameExistException() {
        super("Username exists.", "The username was already taken.");
    }
}

