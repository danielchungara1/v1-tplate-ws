package com.tplate.layers.business.exceptions.user;

import com.tplate.layers.business.exceptions.BusinessException;

public class UserCannotBeDeleteException extends BusinessException {

    public UserCannotBeDeleteException(Long id) {
        super("User cannot be deleted.", "The user ID cannot be deleted. "  + id);
    }

    public static void throwsException(Long id) throws UserCannotBeDeleteException {
        throw new UserCannotBeDeleteException(id);
    }
}
