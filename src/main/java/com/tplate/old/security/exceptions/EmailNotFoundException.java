package com.tplate.old.security.exceptions;

import com.tplate.layers.business.exceptions.BusinessException;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException() {
        super("Email not found.", "The email provided is not recognized.");
    }


}
