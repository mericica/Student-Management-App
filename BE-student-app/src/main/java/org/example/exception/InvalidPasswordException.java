package org.example.exception;

public class InvalidPasswordException extends StudentAppException{

    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
