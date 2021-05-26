package com.tplate.layers.business.exceptions.role;

import com.tplate.layers.business.exceptions.BusinessException;

public class RoleMustNotBeDeletedException extends BusinessException {

    public RoleMustNotBeDeletedException(String name) {
        super("The role must not be deleted." , "This Role: " + name + " mustn't be deleted.");
    }

    public static void throwsException(String name) throws RoleMustNotBeDeletedException {
        throw new RoleMustNotBeDeletedException(name);
    }
}
