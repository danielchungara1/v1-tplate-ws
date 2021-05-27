package com.tplate.layers.business.exceptions.category;

import com.tplate.layers.business.exceptions.BusinessException;

public class CategoryNameExistException extends BusinessException {

    public CategoryNameExistException(String name) {
        super("Category name exists.", "The category name was already taken. " + name);
    }

    public static void throwsException(String name) throws CategoryNameExistException {
        throw new CategoryNameExistException(name);
    }

}

