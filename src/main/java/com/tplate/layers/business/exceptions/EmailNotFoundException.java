package com.tplate.layers.business.exceptions;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException() {
        super("Email not found.", "The email provided is not recognized.");
    }


}
