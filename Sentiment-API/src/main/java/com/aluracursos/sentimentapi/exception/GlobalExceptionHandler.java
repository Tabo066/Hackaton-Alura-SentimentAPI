package com.aluracursos.sentimentapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
//import com.aluracursos.sentimentapi.exception.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalError(Exception ex) {

        log.error("Error interno inesperado en SentimentAPI", ex);

        ErrorResponse error = new ErrorResponse(
                "Error interno del servidor",
                "Ocurrió un error inesperado...intente más tarde."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(DsServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleDsServiceUnavailable(
                                         DsServiceUnavailableException ex) {

        log.error("Fallo al comunicarse con el servicio DS", ex);

        ErrorResponse error = new ErrorResponse(
                "Bad Gateway",
                "Servicio de análisis no disponible. Intente más tarde."
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }
}
