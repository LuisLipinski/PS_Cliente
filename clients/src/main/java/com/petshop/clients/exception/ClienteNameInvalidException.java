package com.petshop.clients.exception;

public class ClienteNameInvalidException extends RuntimeException{
    public ClienteNameInvalidException(String message) {
        super(message);
    }
}
