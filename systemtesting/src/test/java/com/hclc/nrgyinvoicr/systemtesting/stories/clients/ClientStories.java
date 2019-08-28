package com.hclc.nrgyinvoicr.systemtesting.stories.clients;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

class ClientStories {
    final Application app;

    ClientStories(Application app) {
        this.app = app;
    }

    void navigateToClientsPage() {
        app.findElement(By.id("ae-button-registries")).click();
        app.clickWith1sTimeout(By.id("ae-button-clients"));
    }
}
