package org.dorum.automation.pages;

import lombok.extern.log4j.Log4j2;
import org.dorum.automation.utils.AshotUtils;
import org.dorum.automation.utils.Config;
import org.dorum.automation.utils.WebDriverContainer;
import org.dorum.automation.utils.WebDriverWaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Log4j2
public class GoogleMapPage extends BasePage {

    private static final By SEARCH_FIELD = By.xpath("//div[@id = 'searchbox']");
    private static final By SEARCH_INPUT_FIELD = By.xpath("//label[@for = 'searchboxinput']/parent::div/following-sibling::input");
    private static final By ZOOM_IN_BTN = By.xpath("//button[@id = 'widget-zoom-in']");
    private static final By MAGNIFY_GLASS_BTN = By.xpath("//button[@id = 'searchbox-searchbutton']");

    public void openGoogleMapsPage() {
        navigateToURL(Config.getUrl());
        WebDriverWaitUtils.waitForElementToBeVisible(SEARCH_INPUT_FIELD);
    }

    public void inputLocation(String location) {
        WebDriverWaitUtils.waitForElementToBeVisible(SEARCH_INPUT_FIELD);
        sendKeys(SEARCH_INPUT_FIELD, location);
    }

    public void clickSearchButton(String cityName) {
        click(MAGNIFY_GLASS_BTN);
        String dynamicXPath = String.format("//img[@alt = '%s weather']", cityName);
        WebDriverWaitUtils.waitForElementToBeVisible(By.xpath(dynamicXPath));
        log.info("Search button was clicked");
        waitForPageToLoad(5);
    }

    public void zoomIn(int times) {
        WebDriverWaitUtils.waitForElementToBeVisible(ZOOM_IN_BTN);
        for (int i = 0; i < times; i++) {
            click(ZOOM_IN_BTN);
        }
        log.info("'Zoom in' is performed {} times", times);
    }

    public boolean isUrlCorrect() {
        return WebDriverContainer.getDriver().getCurrentUrl().contains(Config.getUrl());
    }

    public boolean isSearchFieldCorrectlyDisplayed(String location) {
        WebElement webElement = WebDriverWaitUtils.waitForElementToBeVisible(SEARCH_FIELD);
        return AshotUtils.compareScreenShots(webElement, "images/google_search_field_" + location.replaceAll("\\s", "_"));
    }
}