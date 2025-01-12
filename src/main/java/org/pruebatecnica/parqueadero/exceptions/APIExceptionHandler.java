package org.pruebatecnica.parqueadero.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    private Map<String,Object> response = new HashMap<>();

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException nfe){
        response.clear();
        response.put("error", nfe.getMessage());
        response.put("time", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlacaException.class)
    public ResponseEntity<?> placaException(PlacaException nfe){
        response.clear();
        response.put("mensaje", nfe.getMessage());
        response.put("time", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WithReferencesException.class)
    public ResponseEntity<?> vehiculoHistorialException(WithReferencesException nfe){
        response.clear();
        response.put("mensaje", nfe.getMessage());
        response.put("time", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
