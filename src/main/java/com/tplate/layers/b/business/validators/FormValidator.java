package com.tplate.layers.b.business.validators;

import com.tplate.old.exceptions.FormValidatorException;

public class FormValidator {

    public static FormValidator evaluate() {
        return new FormValidator();
    }

    public FormValidator isRequired(String value, String name) throws FormValidatorException {

        if (value == null || value.equals("") || value.trim().length() <= 0) {
            throw new FormValidatorException(name + " is required.");
        } else {
            return this;
        }
    }

    public FormValidator isEmail(String value, String name) throws FormValidatorException {
        if (value == null || !value.contains("@") || !value.contains(".")) {
            throw new FormValidatorException(name + " invalid format.");
        } else {
            return this;
        }
    }
}
