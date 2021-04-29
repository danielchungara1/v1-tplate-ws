package com.tplate.layers.business.exceptions;

public class ResetCodeNotMatchingException extends BusinessException {

    public ResetCodeNotMatchingException(String code) {
        super("Code is incorrect. " + code, "The code provided not match.");
    }

    public static void throwsException(String code) throws ResetCodeNotMatchingException {
        throw new ResetCodeNotMatchingException(code);
    }
}
