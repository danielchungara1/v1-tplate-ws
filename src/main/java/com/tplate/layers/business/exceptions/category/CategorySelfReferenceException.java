package com.tplate.layers.business.exceptions.category;

import com.tplate.layers.business.exceptions.BusinessRuntimeException;

public class CategorySelfReferenceException extends BusinessRuntimeException {

    public CategorySelfReferenceException(String name) {
        super("Self reference category is invalid.", "Self reference categories is invalid: " + name);
    }

    public static void throwsException(String name) throws CategorySelfReferenceException {
        throw new CategorySelfReferenceException(name);
    }

}

