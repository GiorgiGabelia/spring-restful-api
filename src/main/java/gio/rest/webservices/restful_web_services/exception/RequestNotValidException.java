package gio.rest.webservices.restful_web_services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestNotValidException extends RuntimeException {
    public RequestNotValidException(String message) {
        super(String.format("Invalid request: %s ", message));
    }
}
