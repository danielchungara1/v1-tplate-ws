package com.tplate.old.security.exceptions;

import com.tplate.layers.business.exceptions.BusinessException;

public class ResetCodeNotMatchingException extends BusinessException {

    public ResetCodeNotMatchingException() {
        super("Code is incorrect.", "The code provided not match.");
    }


}
