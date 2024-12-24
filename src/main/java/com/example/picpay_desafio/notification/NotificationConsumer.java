package com.example.picpay_desafio.notification;

import com.example.picpay_desafio.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;


@Service
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
    private final RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                //.baseUrl("https://util.devi.tools/api/v1/notify")
                .baseUrl("http://localhost:8080/mock/notification") // URL do mock
                .build();
    }

//    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio")
//    public void receiveNotification(Transaction transaction){
//        LOGGER.info("notifying transaction {}...", transaction);
//
//        try {
//            var response = restClient.post()
//                    .body(transaction)
//                    .retrieve()
//                    .toBodilessEntity(); // resposta 204 sem nenhum body
//
//            if (!response.getStatusCode().is2xxSuccessful() || response.getStatusCode().isError()) {
//                throw new NotificationException("Failed to notify transaction: " + transaction);
//
//            }
//            LOGGER.info("Notification sent successfully for transaction {}", response.getBody());
//
//        } catch (RestClientException e) {
//            LOGGER.error("Error while notifying transaction {}: {}", transaction, e.getMessage());
//            throw new NotificationException("Error notifying transaction: " + transaction);
//        }
//    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio")
    public void receiveNotification(Transaction transaction) {
        LOGGER.info("Notifying transaction {}...", transaction);

        try {
            var response = restClient.post()
                    .body(transaction) // Envia a transação para o mock
                    .retrieve()
                    .toEntity(MockResponse.class);

            if (!"notify send".equals(response.getBody().getStatus())) {
                throw new NotificationException("Notification failed for transaction: " + transaction);
            }
            LOGGER.info("Notification sent successfully for transaction {}", transaction);

        } catch (RestClientException e) {
            LOGGER.error("Error while notifying transaction {}: {}", transaction, e.getMessage());
            throw new NotificationException("Error notifying transaction: " + transaction);
        }
    }

    public static class MockResponse {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
