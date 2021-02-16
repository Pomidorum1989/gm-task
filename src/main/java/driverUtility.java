import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class driverUtility {

    public static WebDriver driver  = null;
    private static final String URL = "https://www.google.co.il/maps";
    public static WebDriverWait wait = null;

    public static void startChromedriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    public static void openGoogleMapsPage(){
        driver.get(URL);
        wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='searchbox']//input[@aria-label='חיפוש במפות Google']")));
    }

    public static void stopDriver(){
        if (!(driver == null)){
            driver.quit();
        }
    }
}