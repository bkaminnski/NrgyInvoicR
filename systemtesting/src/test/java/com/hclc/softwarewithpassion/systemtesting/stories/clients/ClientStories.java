package com.hclc.softwarewithpassion.systemtesting.stories.clients;

import com.hclc.softwarewithpassion.systemtesting.Application;
import org.openqa.selenium.By;

class ClientStories {
    final Application app;

    ClientStories(Application app) {
        this.app = app;
    }

    void navigateToClientsPage() {
        app.clickWith1sTimeout(By.id("ae-button-registries"));
        app.clickWith1sTimeout(By.id("ae-button-clients"));
    }
}
