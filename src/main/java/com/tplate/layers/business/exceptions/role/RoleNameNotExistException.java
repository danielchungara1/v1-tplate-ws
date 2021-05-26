package com.tplate.layers.business.exceptions.role;

import com.tplate.layers.business.exceptions.BusinessException;

public class RoleNameNotExistException extends BusinessException {

    public RoleNameNotExistException(String name) {
        super("Role name not exists.", "The role name wasn't exist. " + name);
    }

    public static void throwsException(String name) throws RoleNameNotExistException {
        throw new RoleNameNotExistException(name);
    }

}

