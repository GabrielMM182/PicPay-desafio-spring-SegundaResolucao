package com.example.picpay_desafio.authorization;

import com.example.picpay_desafio.transaction.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizerService {
    private RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v2/authorize")
                .build();
    }

    public void authorize(Transaction transaction) {
        restClient.get().retrieve().toEntity(Authorization.class);

    }


}
