package com.tplate.layers.b.business.exceptions;

public class CredentialsNotFoundException extends RestException {

    public CredentialsNotFoundException() {
        super("Credentials not found.", "The credentials ID sent is not recognized.");
    }
}
