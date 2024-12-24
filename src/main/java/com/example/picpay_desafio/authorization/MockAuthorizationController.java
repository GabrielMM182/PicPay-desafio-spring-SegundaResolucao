package com.example.picpay_desafio.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/mock/authorization")
public class MockAuthorizationController {

    @PostMapping
    public ResponseEntity<?> mockAuthorization(@RequestBody Object transaction) {
        // Simula respostas com base em uma lógica simples
        boolean isAuthorized = Math.random() < 0.7; // 70% de chance de ser autorizado

        // Retornando como JSON, com o tipo de conteúdo correto
        if (isAuthorized) {
            return ResponseEntity.ok().body("{\"status\": \"success\"}");
        } else {
            return ResponseEntity.ok().body("{\"status\": \"invalid\"}");
        }
    }
}