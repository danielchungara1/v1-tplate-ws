package com.tplate.layers.business.exceptions.user;

import com.tplate.layers.business.exceptions.BusinessException;

public class UsernameExistException extends BusinessException {

    public UsernameExistException(String username) {
        super("Username exists.", "The username was already taken. " + username);
    }

    public static void throwsException(String username) throws UsernameExistException {
        throw new UsernameExistException(username);
    }
}

