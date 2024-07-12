package org.example.exception;

import lombok.Getter;

@Getter
public abstract class StudentAppException extends RuntimeException {
    private final ErrorCode errorCode;

    public StudentAppException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }
}
