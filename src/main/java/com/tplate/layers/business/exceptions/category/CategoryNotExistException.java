package com.tplate.layers.business.exceptions.category;

import com.tplate.layers.business.exceptions.BusinessRuntimeException;

public class CategoryNotExistException extends BusinessRuntimeException {

    public CategoryNotExistException(Long id) {
        super("Category not found." , "The category sent is not recognized. " + id);
    }

    public static void throwsException(Long id) throws CategoryNotExistException {
        throw new CategoryNotExistException(id);
    }
}
