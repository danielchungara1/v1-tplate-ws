package com.tplate.old.security.exceptions;

import com.tplate.layers.business.exceptions.BusinessException;

public class ResetCodeExpiredException extends BusinessException {

    public ResetCodeExpiredException() {
        super("Code has expired.", "The code provided has expired.");
    }


}
