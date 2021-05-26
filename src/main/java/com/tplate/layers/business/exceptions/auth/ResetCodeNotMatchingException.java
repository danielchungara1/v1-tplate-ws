package com.tplate.layers.business.exceptions.auth;

import com.tplate.layers.business.exceptions.BusinessException;

public class ResetCodeNotMatchingException extends BusinessException {

    public ResetCodeNotMatchingException(String code) {
        super("Code invalid." , "The code provided not match. " + code);
    }

    public static void throwsException(String code) throws ResetCodeNotMatchingException {
        throw new ResetCodeNotMatchingException(code);
    }
}
