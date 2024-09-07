package gio.rest.webservices.restful_web_services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex) {
        var err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, RequestNotValidException.class})
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorDetails err = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        System.out.println(ex.getParameter());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
