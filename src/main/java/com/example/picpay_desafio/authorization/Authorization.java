package com.example.picpay_desafio.authorization;

public record Authorization (
    String message
) {
    public boolean isAuthorized() {
        return message.equals("success");
    }
}
