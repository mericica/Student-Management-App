package org.example.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CnpValidator {
    public static boolean validateCnp(String cnp) {
        return cnp.matches("[0-9]+") && cnp.length() == 13;
    }
}
