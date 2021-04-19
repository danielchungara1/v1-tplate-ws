package com.tplate.layers.b.business.exceptions;

public class RoleNotFoundException extends RestException {

    public RoleNotFoundException() {
        super("Rol not found.", "The rol sent is not recognized.");
    }
}