package com.tplate.layers.business.exceptions;

import java.util.List;

public class RoleWithoutPermissionsException extends BusinessException {

    public RoleWithoutPermissionsException(List<Long> permissionIds) {
        super("Permissions are required" , "We've received an empty array of permissions. " + permissionIds);
    }

    public static void throwsException(List<Long> permissionIds) throws RoleWithoutPermissionsException {
        throw new RoleWithoutPermissionsException(permissionIds);
    }
}
