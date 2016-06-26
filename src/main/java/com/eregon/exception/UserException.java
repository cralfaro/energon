package com.eregon.exception;

/**
 * Created by ruben on 26/06/16.
 */
public class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Exception ex) {
        super(message, ex);
    }
}
