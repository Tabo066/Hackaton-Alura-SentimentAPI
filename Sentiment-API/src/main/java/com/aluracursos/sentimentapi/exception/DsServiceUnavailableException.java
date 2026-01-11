package com.aluracursos.sentimentapi.exception;

public class DsServiceUnavailableException  extends RuntimeException{

    private final String traceId;

    public DsServiceUnavailableException(String message,String traceId){
        super(message);
        this.traceId = traceId;
    }

    public DsServiceUnavailableException(String message, String traceId,Throwable cause){
        super(message,cause);
        this.traceId=traceId;
    }

    public String getTraceId() {
        return traceId;
    }

}
