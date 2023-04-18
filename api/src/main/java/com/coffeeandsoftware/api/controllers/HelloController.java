package com.coffeeandsoftware.api.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Message {
        private String message;
    }

    @GetMapping
    public ResponseEntity<?> getHelloMessage() {
        Message message = new Message("Hello, World!!");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
