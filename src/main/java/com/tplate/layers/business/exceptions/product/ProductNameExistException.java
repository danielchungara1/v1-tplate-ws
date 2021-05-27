package com.tplate.layers.business.exceptions.product;

import com.tplate.layers.business.exceptions.BusinessException;

public class ProductNameExistException extends BusinessException {

    public ProductNameExistException(String name) {
        super("Product name exists.", "The product name was already taken. " + name);
    }

    public static void throwsException(String name) throws ProductNameExistException {
        throw new ProductNameExistException(name);
    }

}

