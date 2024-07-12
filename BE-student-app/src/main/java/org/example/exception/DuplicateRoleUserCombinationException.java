package org.example.exception;


public class DuplicateRoleUserCombinationException extends StudentAppException{

    public DuplicateRoleUserCombinationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
