package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.users;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

public class UserLoginStory {
    private final Application app;

    public UserLoginStory(Application app) {
        this.app = app;
    }

    public void userLogsIn() {
        app.findTimeoutableElementWith30sTimeout(By.id("ae-input-email")).sendKeys("alice@softwarewithpassion.com");
        app.findTimeoutableElementWith30sTimeout(By.id("ae-input-password")).sendKeys("password123");
        app.clickWith30sTimeout(By.id("ae-button-log-in"));
        app.waitUpTo30sUntilElementIsVisible(By.id("ae-card-dashboard"));
    }
}
