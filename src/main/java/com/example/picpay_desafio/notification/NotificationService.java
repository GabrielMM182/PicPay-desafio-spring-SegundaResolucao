package com.example.picpay_desafio.notification;

import com.example.picpay_desafio.transaction.Transaction;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }
    public void notify(Transaction transaction) {
        notificationProducer.sendNotification(transaction);
    }
}
