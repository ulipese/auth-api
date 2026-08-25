package com.soa.authapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<Map<String, String>> getAdmin() {
        return ResponseEntity.ok(Map.of(
                "message", "Acesso concedido ao painel de administração!",
                "status", "200"
        ));
    }
}
