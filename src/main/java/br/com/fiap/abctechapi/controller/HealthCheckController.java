package br.com.fiap.abctechapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {
    public ResponseEntity<Object> status() {
        return ResponseEntity.ok().build();
    }
}
