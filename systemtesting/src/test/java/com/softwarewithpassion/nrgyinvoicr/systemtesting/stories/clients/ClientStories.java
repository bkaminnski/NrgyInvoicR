package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

class ClientStories {
    final Application app;

    ClientStories(Application app) {
        this.app = app;
    }

    void navigateToClientsPage() {
        app.clickWith30sTimeout(By.id("ae-button-registries"));
        app.clickWith30sTimeout(By.id("ae-button-clients"));
    }
}
