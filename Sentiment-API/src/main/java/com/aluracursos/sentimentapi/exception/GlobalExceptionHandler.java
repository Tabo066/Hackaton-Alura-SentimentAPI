package com.aluracursos.sentimentapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
//•	Intercepta errores globalmente
//•	No ensucia el controlador
@Slf4j
public class GlobalExceptionHandler{

    //Captura solo errores de validacion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException ex){
        //respuesta clara y simple
        Map<String,String>errors = new HashMap<>();
        ex.getBindingResult()
                //Nos permite: Saber qué campo falló
                //             Mostrar el mensaje exacto que se definio en el DTO
                .getFieldErrors()
                .forEach(error->
                   errors.put(error.getField(), error.getDefaultMessage())
                );
        //🔴 Log del error (lado servidor)
        log.warn("Error de validación en request:{}",errors);
        return ResponseEntity
                //Codigo HTTP correcto
                //Estandar REST
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
