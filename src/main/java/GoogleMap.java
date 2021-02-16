import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.io.File;
import java.io.IOException;

public class GoogleMap extends driverUtility {
    private By searchInputField = By.xpath("//div[@id='searchbox']//input[@aria-label='חיפוש במפות Google']");
    private By zoomIN = By.xpath("//div/button[@id='widget-zoom-in']/div");
    private By title = By.xpath("//div[@class = 'section-hero-header-title-top-container']//h1//span[1]");

    public GoogleMap() {
    }

    public void inputLocation(String location){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputField));
        driver.findElement(searchInputField).sendKeys(location);
    }

    public void clickSearchButton(){
        driver.findElement(searchInputField).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
    }

    public void zoomIn(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(zoomIN));
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(zoomIN).click();
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    public void takeScreenShot(String fileName) {
        TakesScreenshot scrShot = ((TakesScreenshot)driver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File("C:\\Users\\Valiantsin_Dorum\\Documents\\gm-test\\src\\main\\resources\\" + fileName);
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkURL(String StringURl){
        return driver.getCurrentUrl().contains(StringURl);
    }
}