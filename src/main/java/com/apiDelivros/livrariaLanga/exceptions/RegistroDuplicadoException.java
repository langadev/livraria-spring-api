package com.apiDelivros.livrariaLanga.exceptions;

public class RegistroDuplicadoException extends RuntimeException{
    public RegistroDuplicadoException(String message){
        super(message);
    }
}