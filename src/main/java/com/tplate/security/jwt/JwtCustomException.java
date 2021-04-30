package com.tplate.security.jwt;

import com.tplate.layers.business.exceptions.BusinessException;

public class JwtCustomException extends BusinessException {

    public JwtCustomException(String message, String details) {
        super(message, details);
    }

    public static void throwsException(String message, String details) throws JwtCustomException {
        throw new JwtCustomException(message, details);
    }
}
