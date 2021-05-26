package com.tplate.layers.business.exceptions.role;

import com.tplate.layers.business.exceptions.BusinessException;

public class RoleNameExistException extends BusinessException {

    public RoleNameExistException(String name) {
        super("Role name exists.", "The role name was already taken. " + name);
    }

    public static void throwsException(String name) throws RoleNameExistException {
        throw new RoleNameExistException(name);
    }

}

