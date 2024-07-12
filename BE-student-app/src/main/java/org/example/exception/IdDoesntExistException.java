package org.example.exception;

public class IdDoesntExistException extends StudentAppException {

    public IdDoesntExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
