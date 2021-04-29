package com.tplate.layers.business.exceptions;

public class EmailExistException extends BusinessException {

    public EmailExistException(String email) {
        super("Email exists. " + email, "The email was already taken.");
    }

    public static void throwsException(String email) throws EmailExistException {
        throw new EmailExistException(email);
    }

}

