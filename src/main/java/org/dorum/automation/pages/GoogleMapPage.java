package org.dorum.automation.pages;

import lombok.extern.log4j.Log4j2;
import org.dorum.automation.utils.utils.Config;
import org.dorum.automation.utils.utils.WebDriverContainer;
import org.dorum.automation.utils.utils.WebDriverWaitUtils;
import org.openqa.selenium.By;

@Log4j2
public class GoogleMapPage extends BasePage {

    By searchInputField = By.xpath("//label[@for='searchboxinput']/parent::div/following-sibling::input");
    By zoomInBtn = By.xpath("//button[@id='widget-zoom-in']");
    By magnifyGlassBtn = By.xpath("//button[@id = 'searchbox-searchbutton']");

    public void openGoogleMapsPage() {
        navigateToURL(Config.getUrl());
        WebDriverWaitUtils.waitForElementToBeVisible(searchInputField);
    }

    public void inputLocation(String location) {
        WebDriverWaitUtils.waitForElementToBeVisible(searchInputField);
        findElement(searchInputField).sendKeys(location);
        log.info("Send the following text: {}", location);
    }

    public void clickSearchButton(String cityName) {
        click(findElement(magnifyGlassBtn));
        String dynamicXPath = String.format("//img[@alt = '%s weather']", cityName);
        WebDriverWaitUtils.waitForElementToBeVisible(By.xpath(dynamicXPath));
        log.info("Search button was clicked");
        waitForPageToLoad(5);
    }

    public void zoomIn(int times) {
        WebDriverWaitUtils.waitForElementToBeVisible(zoomInBtn);
        for (int i = 0; i < times; i++) {
            findElement(zoomInBtn).click();
        }
        log.info("Zoom in is performed {} times", times);
    }

    public boolean isUrlCorrect() {
        return WebDriverContainer.getDriver().getCurrentUrl().contains(Config.getUrl());
    }
}