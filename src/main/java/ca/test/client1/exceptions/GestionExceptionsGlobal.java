package ca.test.client1.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionExceptionsGlobal {
    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundResourceException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
