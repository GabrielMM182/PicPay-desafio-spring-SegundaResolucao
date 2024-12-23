package com.example.picpay_desafio.notification;

import com.example.picpay_desafio.transaction.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final KafkaTemplate<String, Transaction> KafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Transaction> KafkaTemplate) {
        this.KafkaTemplate = KafkaTemplate;
    }

    public void sendNotification(Transaction transaction) {
        KafkaTemplate.send("transaction-notification", transaction);
    }
}
