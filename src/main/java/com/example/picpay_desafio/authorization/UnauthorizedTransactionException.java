package com.example.picpay_desafio.authorization;

public class UnauthorizedTransactionException extends  RuntimeException{

    public UnauthorizedTransactionException(String message) {
        super(message);
    }
}
