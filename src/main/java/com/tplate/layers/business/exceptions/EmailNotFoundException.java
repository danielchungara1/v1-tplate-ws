package com.tplate.layers.business.exceptions;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException(String email) {
        super("Email not found.", "The email provided is not recognized. " + email);
    }

    public static void throwsException(String email) throws EmailNotFoundException {
        throw new EmailNotFoundException(email);
    }

}
