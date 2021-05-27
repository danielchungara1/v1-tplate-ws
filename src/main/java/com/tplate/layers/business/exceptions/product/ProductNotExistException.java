package com.tplate.layers.business.exceptions.product;

import com.tplate.layers.business.exceptions.BusinessException;

public class ProductNotExistException extends BusinessException {

    public ProductNotExistException(Long id) {
        super("Product not found." , "The product sent is not recognized. " + id);
    }

    public static void throwsException(Long id) throws ProductNotExistException {
        throw new ProductNotExistException(id);
    }
}
