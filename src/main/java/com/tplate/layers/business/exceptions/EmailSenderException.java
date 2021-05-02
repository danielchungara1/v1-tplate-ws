package com.tplate.layers.business.exceptions;

public class EmailSenderException extends BusinessException {

    public EmailSenderException(String details) {
        super("Email not sent.", details);
    }

    public static void throwsException(String details) throws EmailSenderException {
        throw new EmailSenderException(details);
    }
}
