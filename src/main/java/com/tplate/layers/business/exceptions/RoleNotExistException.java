package com.tplate.layers.business.exceptions;

public class RoleNotExistException extends BusinessException {

    public RoleNotExistException() {
        super("Rol not found.", "The rol sent is not recognized.");
    }

    public static void throwsException() throws RoleNotExistException {
        throw new RoleNotExistException();
    }
}
