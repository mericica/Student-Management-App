package org.example.exception;

public enum ErrorCode {
    CNPALREADYINUSE("Another User already exists with that cnp"),
    INVALIDID("No User with this ID was found"),
    INVALIDCNP("CNP is invalid"),
    INVALIDPASSWORD("Password should start with an uppercase letter, followed by lowercase letters and at least a number"),
    
    INVALIDROLE("Role id doesnt exist"),
    
    DUPLICATEROLE("This role is already assigned to the student");

    private final String label;
    ErrorCode(String label) {
        this.label = label;
    }
}

