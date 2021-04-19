package com.tplate.layers.b.business.exceptions;

public class PasswordRequiredException extends RestException {

    public PasswordRequiredException() {
        super("Password is required.", "For create new user you must specify the password.");
    }
}

