package com.example.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple controller to validate the backend is working.
 * Add your endpoints and business logic here. Secure endpoints in production.
 */
@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from backend - replace this with JSON response and DTOs.";
    }
}
