package org.dorum.automation;

import lombok.extern.log4j.Log4j2;
import org.dorum.automation.Pages.GoogleMapPage;
import org.dorum.automation.Pages.utils.WebDriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Log4j2
public class GoogleMapsTest extends TestBase {

    @DataProvider(name = "cities", parallel = false)
    public Object[][] dataProvider() {
        return new Object[][]{
                new String[]{"Tel Aviv-Yafo",},
                new String[]{"Minsk"},
                new String[]{"New York"}
        };
    }

    @Test(description = "Google Maps Test", dataProvider = "cities")
    public void googleMapsTest(String city) {
        log.info("Argument {}, Thread ID: {}, Session ID {} is started", city,
                Thread.currentThread().getId(), ((RemoteWebDriver) WebDriverFactory.getDriver()).getSessionId().toString());
        googleMaps = new GoogleMapPage(WebDriverFactory.getDriver(), waitUtils);
        googleMaps.openGoogleMapsPage();
        googleMaps.inputLocation(city);
        googleMaps.clickSearchButton(city);
        googleMaps.takeScreenShot(1 + city);
        googleMaps.zoomIn();
        googleMaps.takeScreenShot(2 + city);
        googleMaps.zoomIn();
        googleMaps.takeScreenShot(3 + city);
        softAssert.assertTrue(googleMaps.isUrlCorrect(), "URL is incorrect");
        softAssert.assertAll();
    }
}