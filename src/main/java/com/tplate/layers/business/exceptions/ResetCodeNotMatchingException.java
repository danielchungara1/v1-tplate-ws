package com.tplate.layers.business.exceptions;

public class ResetCodeNotMatchingException extends BusinessException {

    public ResetCodeNotMatchingException(String code) {
        super("Code not valid." , "The code provided not match. " + code);
    }

    public static void throwsException(String code) throws ResetCodeNotMatchingException {
        throw new ResetCodeNotMatchingException(code);
    }
}
