package com.tplate.layers.business.exceptions;

public class ResetCodeNotFoundException extends BusinessException {

    public ResetCodeNotFoundException(String code) {
        super("Code not found." , "The code provided not exist. " + code);
    }

    public static void throwsException(String code) throws ResetCodeNotFoundException {
        throw new ResetCodeNotFoundException(code);
    }

}
