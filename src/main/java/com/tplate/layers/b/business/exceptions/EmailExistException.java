package com.tplate.layers.b.business.exceptions;

public class EmailExistException extends RestException {

    public EmailExistException() {
        super("Email exists.", "The email was already taken.");
    }


}

