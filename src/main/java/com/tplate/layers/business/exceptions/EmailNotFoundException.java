package com.tplate.layers.business.exceptions;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException(String email) {
        super("Email not found. " + email, "The email provided is not recognized.");
    }

    public static void throwsException(String email) throws EmailNotFoundException {
        throw new EmailNotFoundException(email);
    }

}
