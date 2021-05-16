package com.tplate.layers.business.exceptions;

public class ResetCodeExpiredException extends BusinessException {

    public ResetCodeExpiredException(String code) {
        super("Code expired.", "The code provided has expired. "  + code);
    }

    public static void throwsException(String code) throws ResetCodeExpiredException {
        throw new ResetCodeExpiredException(code);
    }

}
