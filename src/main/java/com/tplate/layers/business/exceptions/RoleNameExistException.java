package com.tplate.layers.business.exceptions;

public class RoleNameExistException extends BusinessException {

    public RoleNameExistException(String name) {
        super("Role exists.", "The role name was already taken. " + name);
    }

    public static void throwsException(String name) throws RoleNameExistException {
        throw new RoleNameExistException(name);
    }

}

