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
                .baseUrl("https://util.devi.tools/api/v1/notify")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio")
    public void receiveNotification(Transaction transaction){
        LOGGER.info("notifying transaction {}...", transaction);

        try {
            var response = restClient.post()
                    .body(transaction)
                    .retrieve()
                    .toBodilessEntity(); // resposta 204 sem nenhum body

            if (!response.getStatusCode().is2xxSuccessful() || response.getStatusCode().isError()) {
                throw new NotificationException("Failed to notify transaction: " + transaction);

            }
            LOGGER.info("Notification sent successfully for transaction {}", response.getBody());

        } catch (RestClientException e) {
            LOGGER.error("Error while notifying transaction {}: {}", transaction, e.getMessage());
            throw new NotificationException("Error notifying transaction: " + transaction);
        }
    }


}
