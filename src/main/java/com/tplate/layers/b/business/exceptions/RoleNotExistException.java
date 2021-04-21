package com.tplate.layers.b.business.exceptions;

public class RoleNotExistException extends BusinessException {

    public RoleNotExistException() {
        super("Rol not found.", "The rol sent is not recognized.");
    }
}
