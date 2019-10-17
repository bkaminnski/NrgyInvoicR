package com.hclc.nrgyinvoicr.systemtesting.stories.users;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

public class UserLoginStory {
    private final Application app;

    public UserLoginStory(Application app) {
        this.app = app;
    }

    public void userLogsIn() {
        app.findElement(By.id("ae-input-email")).sendKeys("alice@softwarewithpassion.com");
        app.findElement(By.id("ae-input-password")).sendKeys("password123");
        app.findElement(By.id("ae-button-log-in")).click();
        app.waitUpTo30sUntilElementIsVisible(By.id("ae-card-dashboard"));
    }
}
