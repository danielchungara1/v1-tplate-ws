package com.tplate.layers.b.business.exceptions;

public class NotImplementedException extends RestException {

    public NotImplementedException() {
        super("Functionality not implemented.", "Not implemented yet. It's in progress.");
    }
}
