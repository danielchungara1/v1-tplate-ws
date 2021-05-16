package com.tplate.layers.business.exceptions;

public class RoleNotExistException extends BusinessException {

    public RoleNotExistException(Long id) {
        super("Role not found." , "The role sent is not recognized. " + id);
    }

    public static void throwsException(Long id) throws RoleNotExistException {
        throw new RoleNotExistException(id);
    }
}
