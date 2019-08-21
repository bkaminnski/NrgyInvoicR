package com.hclc.nrgyinvoicr.systemtesting;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class SystemTest {
    protected static Application app;

    @BeforeAll
    static void beforeAll() {
        app = new Application();
    }

    @AfterAll
    static void afterAll() {
        app.quit();
    }
}
