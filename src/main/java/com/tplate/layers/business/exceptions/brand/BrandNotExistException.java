package com.tplate.layers.business.exceptions.brand;

import com.tplate.layers.business.exceptions.BusinessException;

public class BrandNotExistException extends BusinessException {

    public BrandNotExistException(Long id) {
        super("Brand not found." , "The brand sent is not recognized. " + id);
    }

    public static void throwsException(Long id) throws BrandNotExistException {
        throw new BrandNotExistException(id);
    }
}
