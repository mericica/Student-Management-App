package org.example.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = CnpAlreadyInUseException.class)
        public ResponseEntity<String> handleCnpAlreadyExistsException(CnpAlreadyInUseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = IdDoesntExistException.class)
    public ResponseEntity<String> handleIdDoesntExistException(IdDoesntExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidCnpException.class)
    public ResponseEntity<String> handleCnpIsnt13CharactersLongException(InvalidCnpException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<String> handlePasswordIsNotValidException(InvalidPasswordException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RoleIdDoesntExistException.class)
    public ResponseEntity<String> handleRoleIdIsNotValidException(RoleIdDoesntExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = DuplicateRoleUserCombinationException.class)
    public ResponseEntity<String> handleDuplicateRoleEntityCombinationException(DuplicateRoleUserCombinationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
