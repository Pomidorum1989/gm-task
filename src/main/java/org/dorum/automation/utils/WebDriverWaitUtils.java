package org.dorum.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class WebDriverWaitUtils {

    private static FluentWait<WebDriver> createFluentWait() {
        return new FluentWait<>(WebDriverContainer.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class);
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        return createFluentWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementToBeVisible(By locator) {
        return createFluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeVisible(WebElement element) {
        return createFluentWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static boolean waitForElementToBeInvisible(By locator) {
        return createFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBePresent(By locator) {
        return createFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean waitForTextToBePresentInElement(By locator, String text) {
        return createFluentWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForUrlToContain(String fraction) {
        return createFluentWait().until(ExpectedConditions.urlContains(fraction));
    }

    public static boolean waitForTitleToBe(String title) {
        return createFluentWait().until(ExpectedConditions.titleIs(title));
    }
}