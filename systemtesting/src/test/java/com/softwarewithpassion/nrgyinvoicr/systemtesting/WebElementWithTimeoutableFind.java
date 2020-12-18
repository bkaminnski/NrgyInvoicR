package com.softwarewithpassion.nrgyinvoicr.systemtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Supplier;

public class WebElementWithTimeoutableFind extends WebElementDelegate {
    private static final long NANOS_IN_SECOND = 1_000_000_000L;

    public WebElementWithTimeoutableFind(WebElement webElement) {
        super(webElement);
    }

    @Override
    public WebElement findElement(By by) {
        return findElementWithTimeout(() -> super.findElement(by));
    }

    @Override
    public List<WebElement> findElements(By by) {
        return findElementWithTimeout(() -> super.findElements(by));
    }

    private <R> R findElementWithTimeout(Supplier<R> supplier) {
        long started = System.nanoTime();
        RuntimeException exception = new RuntimeException();
        while (System.nanoTime() < started + 30 * NANOS_IN_SECOND) {
            try {
                return supplier.get();
            } catch (NoSuchElementException e) {
                exception = e;
                waitFor125Millis();
            }
        }
        throw exception;
    }

    private void waitFor125Millis() {
        try {
            Thread.sleep(125L);
        } catch (InterruptedException ignored) {
        }
    }
}
