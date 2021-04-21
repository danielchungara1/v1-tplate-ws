package com.tplate.layers.b.business.exceptions;

public class NotImplementedException extends BusinessException {

    public NotImplementedException() {
        super("Functionality not implemented.", "Not implemented yet. It's in progress.");
    }
}
