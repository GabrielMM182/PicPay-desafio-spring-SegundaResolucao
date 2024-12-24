package com.example.picpay_desafio.authorization;

import com.example.picpay_desafio.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizerService.class);

    private final RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                //.baseUrl("https://util.devi.tools/api/v2/authorize")
                .baseUrl("http://localhost:8080/mock/authorization") // URL do mock
                .build();
    }

    public void authorize(Transaction transaction) {
        LOGGER.info("Authorizing transaction {}...", transaction);

        var response = restClient.post()
                .body(transaction) // Envia a transação para o mock
                .retrieve()
                .body(String.class); // Espera a resposta como String

        if (!response.contains("success")) {
            throw new UnauthorizedTransactionException("Unauthorized transaction!");
        }

        LOGGER.info("Transaction authorized successfully: {}...", transaction);
    }


//    public void authorize(Transaction transaction) {
//        LOGGER.info("authorizing transaction {}...", transaction);
//
//        var response =  restClient.get()
//              .retrieve()
//              .toEntity(Authorization.class);
//
//      if (response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
//          throw new UnauthorizedTransactionException("Unauthorized transaction!");
//      }
//        LOGGER.info("Transaction authorized {}...", transaction);
//
//    }


}
