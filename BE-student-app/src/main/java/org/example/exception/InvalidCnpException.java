package org.example.exception;

public class InvalidCnpException extends StudentAppException{


    public InvalidCnpException(ErrorCode errorCode) {
        super(errorCode);
    }
}
