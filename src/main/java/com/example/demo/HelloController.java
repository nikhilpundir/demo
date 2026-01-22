package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Standard Logging
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final GreetingRepository repository;

    @GetMapping("/{message}")
    public Greeting sayHello(@PathVariable String message) {
        log.info("Received request to say hello: {}", message);
        return repository.save(new Greeting(null, "Hello, " + message));
    }
}