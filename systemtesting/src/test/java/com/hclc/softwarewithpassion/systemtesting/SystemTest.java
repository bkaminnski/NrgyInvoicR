package com.hclc.softwarewithpassion.systemtesting;

import com.hclc.softwarewithpassion.systemtesting.stories.users.UserLoginStory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class SystemTest {
    protected static Application app;

    @BeforeAll
    static void beforeAll() {
        app = new Application();
        new UserLoginStory(app).userLogsIn();
    }

    @AfterAll
    static void afterAll() {
        app.quit();
    }
}
