package com.aluracursos.sentimentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    //Captura solo errores de validacion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException ex){
        //respuesta clara y simple
        Map<String,String>errors = new HashMap<>();
        ex.getBindingResult()
                //Nos permite: Saber qué campo falló
                //Mostrar el mensaje exacto que se definio en el DTO
                .getFieldErrors()
                .forEach(error->
                   errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                //Codigo HTTP correcto
                //Estandar REST
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
