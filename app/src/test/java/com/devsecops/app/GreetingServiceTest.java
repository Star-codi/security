package com.devsecops.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreetingServiceTest {

    private GreetingService service;

    @BeforeEach
    void setUp() {
        service = new GreetingService();
    }

    @Test
    void greet_validName_returnsGreeting() {
        String result = service.greet("Alice");
        assertEquals("Hello, Alice!", result);
    }

    @Test
    void greet_nullName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet(null));
    }

    @Test
    void greet_blankName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.greet("  "));
    }

    @Test
    void appStatus_returnsRunning() {
        String status = service.appStatus();
        assertTrue(status.contains("running"));
    }
}
