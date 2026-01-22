package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GreetingRepository repository;

    // Runs before EACH test to ensure a clean slate
    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testActuatorHealth() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.db.status").value("UP"));
    }

    @Test
    void testHelloController() throws Exception {
        // 1. Call the API
        mockMvc.perform(get("/hello/Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Java"));

        // 2. Verify Database Persistence
        assertEquals(1, repository.count());
        
        Greeting saved = repository.findAll().getFirst();
        assertEquals("Hello, Java", saved.getMessage());
    }
}
