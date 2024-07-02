package org.dorum.automation.pages;

import lombok.extern.log4j.Log4j2;
import org.dorum.automation.utils.utils.Config;
import org.dorum.automation.utils.utils.WebDriverContainer;
import org.dorum.automation.utils.utils.WebDriverWaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

@Log4j2
public class GoogleMapPage extends BasePage {

//    @FindBy(xpath = "//label[@for='searchboxinput']/parent::div/following-sibling::input")
//    private WebElement searchInputField;
//    @FindBy(xpath = "//button[@id='widget-zoom-in']")
//    private WebElement zoomInBtn;
//    @FindBy(xpath = "//button[@id = 'searchbox-searchbutton']")
//    private WebElement magnifyGlassBtn;

    public GoogleMapPage() {
//        PageFactory.initElements(WebDriverContainer.getDriver(), this);
//        PageFactory.initElements(new AjaxElementLocatorFactory(WebDriverContainer.getDriver(), 10), this);
    }

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
//        searchInputField.sendKeys(location);
        log.info("Send the following text: {}", location);
    }

    public void clickSearchButton(String cityName) {
        click(findElement(magnifyGlassBtn));
//        click(magnifyGlassBtn);
        String dynamicXPath = String.format("//img[@alt = '%s weather']", cityName);
        WebDriverWaitUtils.waitForElementToBeVisible(By.xpath(dynamicXPath));
        log.info("Search button was clicked");
        waitForPageToLoad(5);
    }

    public void zoomIn(int times) {
        WebDriverWaitUtils.waitForElementToBeVisible(zoomInBtn);
        for (int i = 0; i < times; i++) {
            findElement(zoomInBtn).click();
//            click(zoomInBtn);
        }
        log.info("Zoom in is performed {} times", times);
    }

    public boolean isUrlCorrect() {
        return WebDriverContainer.getDriver().getCurrentUrl().contains(Config.getUrl());
    }
}