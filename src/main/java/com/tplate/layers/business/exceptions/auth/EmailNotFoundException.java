package com.tplate.layers.business.exceptions.auth;

import com.tplate.layers.business.exceptions.BusinessException;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException(String email) {
        super("Email not found.", "The email provided is not recognized. " + email);
    }

    public static void throwsException(String email) throws EmailNotFoundException {
        throw new EmailNotFoundException(email);
    }

}
