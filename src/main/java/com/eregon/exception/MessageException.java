package com.eregon.exception;

/**
 * Created by ruben on 26/06/16.
 */
public class MessageException extends Exception {

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Exception ex) {
        super(message, ex);
    }
}
