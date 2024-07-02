package org.dorum.automation.pages;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.dorum.automation.utils.utils.WebDriverContainer;
import org.dorum.automation.utils.utils.WebDriverFactory;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

@Log4j2
public class BasePage {

    public void navigateToURL(String url) {
        WebDriverContainer.getDriver().get(url);
        log.info("URL {} was opened", url);
    }

    public WebElement findElement(By by) {
        return WebDriverContainer.getDriver().findElement(by);
    }

    public void sendKeys(WebElement element, String inputText) {
        element.sendKeys(inputText);
        log.info("{} text was sent", inputText);
    }

    public void click(WebElement element) {
        element.click();
        log.info("Click was performed");
    }

    public void takeScreenShot(String fileName) {
        File SrcFile, DestFile;
        String name = fileName.replaceAll("\\s", "");
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) WebDriverContainer.getDriver());
            SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            DestFile = new File(String.format("target\\%s.png", name));
            FileUtils.copyFile(SrcFile, DestFile);
            String winHandle = WebDriverContainer.getDriver().getWindowHandle();
            log.info("Screenshot {} of window handle {} was created", name, winHandle);
        } catch (WebDriverException | IOException e) {
            log.error("Unable to create screenshot: {}", e.getMessage());
        }
    }

    public void waitForPageToLoad(int seconds) {
        boolean pageLoaded = false;
        int attempts = 0;
        while (!pageLoaded && attempts < seconds) {
            pageLoaded = ((JavascriptExecutor) WebDriverContainer.getDriver()).executeScript("return document.readyState").equals("complete");
            if (!pageLoaded) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.warn("Page still loading");
                }
            }
            attempts++;
        }
        if (pageLoaded) {
            log.info("Page is fully loaded in {} second(s)", attempts);
        } else {
            log.info("Page may not have fully loaded after {} second(s)", seconds);
        }
    }
}
