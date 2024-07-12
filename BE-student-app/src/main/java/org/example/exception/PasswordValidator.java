package org.example.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PasswordValidator {
    public static boolean validatePassword(String password) {

        return password.matches("^[A-Z][a-z]+\\d+");
    }
}
