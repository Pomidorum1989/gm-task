package org.dorum.automation.Pages;

import lombok.extern.log4j.Log4j2;
import org.dorum.automation.Pages.utils.Config;
import org.dorum.automation.Pages.utils.WebDriverWaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Log4j2
public class GoogleMapPage extends BasePage {

    @FindBy(xpath = "//label[@for='searchboxinput']/parent::div/following-sibling::input")
    private WebElement searchInputField;
    @FindBy(xpath = "//button[@id='widget-zoom-in']")
    private WebElement zoomInBtn;
    @FindBy(xpath = "//button[@id = 'searchbox-searchbutton']")
    private WebElement magnifyGlassBtn;

    public GoogleMapPage(WebDriver driver, WebDriverWaitUtils waitUtils) {
        super(driver, waitUtils);
    }

    public void openGoogleMapsPage() {
        navigateToURL(Config.getUrl());
        waitUtils.waitForElementToBeVisible(searchInputField);
    }

    public void inputLocation(String location) {
        waitUtils.waitForElementToBeVisible(searchInputField);
        searchInputField.sendKeys(location);
        log.info("Send the following text: {}", location);
    }

    public void clickSearchButton(String cityName) {
        click(magnifyGlassBtn);
        String dynamicXPath = String.format("(//*[text() = '%s'])[2]", cityName);
        waitUtils.waitForElementToBeVisible(By.xpath(dynamicXPath));
        log.info("Search button was clicked");
        waitForPageToLoad(5);
    }

    public void zoomIn() {
        waitUtils.waitForElementToBeVisible(zoomInBtn);
        click(zoomInBtn);
        log.info("Zoom in is performed");
    }

    public boolean isUrlCorrect() {
        return driver.getCurrentUrl().contains(Config.getUrl());
    }
}