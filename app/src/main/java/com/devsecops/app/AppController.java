package com.devsecops.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello() {
        return ResponseEntity.ok(Map.of(
            "message", "Hello from DevSecOps App!",
            "status",  "running"
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @GetMapping("/greet/{name}")
    public ResponseEntity<Map<String, String>> greet(@PathVariable String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Name must not be blank"));
        }
        return ResponseEntity.ok(Map.of("message", "Hello, " + name + "!"));
    }
}
