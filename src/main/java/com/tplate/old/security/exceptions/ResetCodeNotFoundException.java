package com.tplate.old.security.exceptions;

import com.tplate.layers.business.exceptions.BusinessException;

public class ResetCodeNotFoundException extends BusinessException {

    public ResetCodeNotFoundException() {
        super("Code not found.", "The code provided not exist.");
    }


}
