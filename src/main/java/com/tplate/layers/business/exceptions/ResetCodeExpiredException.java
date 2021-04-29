package com.tplate.layers.business.exceptions;

public class ResetCodeExpiredException extends BusinessException {

    public ResetCodeExpiredException(String code) {
        super("Code has expired. " + code, "The code provided has expired.");
    }

    public static void throwsException(String code) throws ResetCodeExpiredException {
        throw new ResetCodeExpiredException(code);
    }

}
