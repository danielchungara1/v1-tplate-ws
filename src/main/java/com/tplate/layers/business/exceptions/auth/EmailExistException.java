package com.tplate.layers.business.exceptions.auth;

import com.tplate.layers.business.exceptions.BusinessException;

public class EmailExistException extends BusinessException {

    public EmailExistException(String email) {
        super("Email exists.", "The email was already taken. " + email);
    }

    public static void throwsException(String email) throws EmailExistException {
        throw new EmailExistException(email);
    }

}

