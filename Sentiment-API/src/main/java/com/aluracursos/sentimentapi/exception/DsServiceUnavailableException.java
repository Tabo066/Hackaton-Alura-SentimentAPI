package com.aluracursos.sentimentapi.exception;

public class DsServiceUnavailableException  extends RuntimeException{

    public DsServiceUnavailableException(String message, Throwable cause){
        super(message,cause);
    }
}
