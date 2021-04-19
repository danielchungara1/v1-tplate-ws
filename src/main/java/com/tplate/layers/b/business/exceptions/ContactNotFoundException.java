package com.tplate.layers.b.business.exceptions;

public class ContactNotFoundException extends RestException {

    public ContactNotFoundException() {
        super("Contact not found.", "The contact ID sent is not recognized.");
    }
}
