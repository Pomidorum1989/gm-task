import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
public class googleMapsTest {

    private GoogleMap googleMap = new GoogleMap();
    private final String location = "Rome";
    private final String siteURL = "https://www.google.co.il/maps";

    @BeforeMethod
    public void openURL(){
        driverUtility.startChromedriver();
        driverUtility.openGoogleMapsPage();
    }

    @Test(description = "Google Maps Test")
    public void googleMapsTest(){
        SoftAssert softAssert = new SoftAssert();

        googleMap.inputLocation(location);
        googleMap.clickSearchButton();
        googleMap.takeScreenShot("Screen1.png");
        googleMap.zoomIn();
        googleMap.takeScreenShot("Screen2.png");
        googleMap.zoomIn();
        googleMap.takeScreenShot("Screen3.png");

        softAssert.assertTrue(googleMap.checkURL(siteURL),"URL is incorrect");
        softAssert.assertAll();
    }

    @AfterTest
    public void closeBrowser(){
        driverUtility.stopDriver();

    }
}