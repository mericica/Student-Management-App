package org.example.exception;

public class RoleIdDoesntExistException extends StudentAppException{

    public RoleIdDoesntExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
