package org.example.exception;

public class CnpAlreadyInUseException extends StudentAppException {

    public CnpAlreadyInUseException(ErrorCode errorCode) {
        super(errorCode);
    }
}
