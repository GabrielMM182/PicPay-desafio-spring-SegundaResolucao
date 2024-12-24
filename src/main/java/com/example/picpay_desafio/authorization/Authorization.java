package com.example.picpay_desafio.authorization;

import static org.apache.kafka.common.requests.FetchMetadata.log;

public record Authorization (
    String message
) {
    public boolean isAuthorized() {
        if (message == null) {
            log.error("Mensagem de autorização está nula.");
            return false;
        }
        return "success".equals(message);
    }}
