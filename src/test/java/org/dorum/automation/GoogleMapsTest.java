package org.dorum.automation;

import lombok.extern.log4j.Log4j2;
import org.dorum.automation.pages.GoogleMapPage;
import org.dorum.automation.utils.WebDriverContainer;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Log4j2
public class GoogleMapsTest extends TestBase {

    @DataProvider(name = "cities", parallel = true)
    public Object[][] dataProvider() {
        return new Object[][]{
                new String[]{"Tel Aviv-Yafo"},
                new String[]{"Minsk"},
                new String[]{"New York"}
        };
    }

    @Test(description = "Google Maps Test", dataProvider = "cities")
    public void googleMapsTest(String city) {
        log.info("Data provider argument {}, Thread ID: {}, Session ID: {} is started", city,
                Thread.currentThread().getId(), ((RemoteWebDriver) WebDriverContainer.getDriver()).getSessionId().toString());
        googleMaps = new GoogleMapPage();
        googleMaps.openGoogleMapsPage();
        googleMaps.inputLocation(city);
        googleMaps.clickSearchButton(city);
        googleMaps.takeScreenShot(1 + "_" + city);
        googleMaps.zoomIn(1);
        googleMaps.takeScreenShot(2 + "_" + city);
        googleMaps.zoomIn(1);
        googleMaps.takeScreenShot(3 + "_" + city);
        softAssert.assertTrue(googleMaps.isUrlCorrect(), "Google maps URL is incorrect");
        softAssert.assertAll();
    }
}