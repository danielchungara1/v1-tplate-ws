package com.tplate.layers.b.business.exceptions;

public class ContactEmailNotFoundException extends RestException {

    public ContactEmailNotFoundException() {
        super("Contact Email not found.", "The contact email sent is not recognized.");
    }
}
