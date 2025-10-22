package com.example.securenote.domain.note.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingApi {

    @GetMapping("/api/ping")
    public String ping() { return "pong"; }
}
