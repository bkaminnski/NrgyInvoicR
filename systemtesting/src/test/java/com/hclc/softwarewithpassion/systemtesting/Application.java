package com.hclc.softwarewithpassion.systemtesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Application {
    private static final String DEV_APPLICATION_URL = "http://localhost:8080";
    private static final String APPLICATION_TITLE = "NrgyInvoicR";
    private final WebDriver driver;

    Application() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(5, SECONDS);
        this.driver.manage().window().setSize(new Dimension(1920, 1080));
        waitForApplicationToStart();
    }

    private void waitForApplicationToStart() {
        boolean ready = false;
        int retries = 0;
        final int maxRetries = 120;
        while (!ready && retries < maxRetries) {
            try {
                lookForPageTitle();
                ready = true;
            } catch (Throwable e) {
                retries++;
                waitFor125Millis();
            }
        }
        if (retries == maxRetries) {
            throw new IllegalStateException("Application has not started within expected time");
        }
    }

    private void lookForPageTitle() {
        String applicationUrl = System.getProperty("application-url", DEV_APPLICATION_URL);
        driver.get(applicationUrl);
        if (!driver.getTitle().equals(APPLICATION_TITLE)) {
            throw new IllegalStateException("Page not yet loaded");
        }
    }

    private void waitFor125Millis() {
        try {
            Thread.sleep(125L);
        } catch (InterruptedException ignored) {
        }
    }

    void quit() {
        driver.quit();
    }

    public void waitUpTo1sUntilElementIsVisible(By by) {
        waitUntilElementIsVisible(by, 1);
    }

    public void waitUpTo30sUntilElementIsVisible(By by) {
        waitUntilElementIsVisible(by, 30);
    }

    private void waitUntilElementIsVisible(By by, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public void clickWith1sTimeout(By by) {
        this.clickWithTimeout(by, 1);
    }

    private void clickWithTimeout(By by, int timeoutInSeconds) {
        try {
            tryClickingWithTimeout(by, timeoutInSeconds);
        } catch (InterruptedException ignored) {
        }
    }

    private void tryClickingWithTimeout(By by, int timeoutInSeconds) throws InterruptedException {
        long started = System.nanoTime();
        while (System.nanoTime() < started + timeoutInSeconds * 1_000_000_000L) {
            try {
                findElement(by).click();
                return;
            } catch (ElementClickInterceptedException e) {
                Thread.sleep(125);
            }
        }
    }

    public String getValueOfElement(By by) {
        return driver.findElement(by).getAttribute("value");
    }

    public void clearElementAndSendKeys(By by, String keysToSend) {
        WebElement element = findElement(by);
        while (!element.getAttribute("value").isEmpty()) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.sendKeys(keysToSend);
    }

    public void hoverOverElement(By by) {
        WebElement element = findElement(by);
        new Actions(driver).moveToElement(element).perform();
    }
}
