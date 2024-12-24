package com.example.picpay_desafio.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock/notification")
public class MockNotificationController {

    @PostMapping
    public ResponseEntity<?> mockNotification(@RequestBody Object transaction) {
        // Simula respostas com base em uma lógica simples
        double random = Math.random();

        if (random < 0.6) {
            return ResponseEntity.ok().body("{\"status\": \"notify send\"}");
        } else if (random < 0.9) {
            return ResponseEntity.ok().body("{\"status\": \"notify waited\"}");
        } else {
            return ResponseEntity.ok().body("{\"status\": \"notify failed\"}");
        }
    }
}