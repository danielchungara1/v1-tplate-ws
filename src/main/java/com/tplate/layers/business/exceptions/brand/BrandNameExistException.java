package com.tplate.layers.business.exceptions.brand;

import com.tplate.layers.business.exceptions.BusinessException;

public class BrandNameExistException extends BusinessException {

    public BrandNameExistException(String name) {
        super("Brand name exists.", "The brand name was already taken. " + name);
    }

    public static void throwsException(String name) throws BrandNameExistException {
        throw new BrandNameExistException(name);
    }

}

