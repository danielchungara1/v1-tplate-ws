package com.tplate.layers.business.exceptions.role;

import com.tplate.layers.business.exceptions.BusinessException;

public class RoleNotExistException extends BusinessException {

    public RoleNotExistException(Long id) {
        super("Role not found." , "The role sent is not recognized. " + id);
    }

    public static void throwsException(Long id) throws RoleNotExistException {
        throw new RoleNotExistException(id);
    }
}
