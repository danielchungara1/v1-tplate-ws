package com.tplate.layers.business.exceptions.permission;

import com.tplate.layers.business.exceptions.BusinessException;

public class PermissionNotExistException extends BusinessException {

    public PermissionNotExistException(Long id) {
        super("Permission not exists.", "The permission ID sent is not recognized. "  + id);
    }

    public static void throwsException(Long id) throws PermissionNotExistException {
        throw new PermissionNotExistException(id);
    }
}
