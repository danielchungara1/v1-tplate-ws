package com.tplate.layers.business.exceptions;

public class EmailExistException extends BusinessException {

    public EmailExistException() {
        super("Email exists.", "The email was already taken.");
    }


    public static void throwsException() throws EmailExistException {
        throw new EmailExistException();
    }
}

