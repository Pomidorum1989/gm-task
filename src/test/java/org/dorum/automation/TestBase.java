package org.dorum.automation;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.dorum.automation.pages.GoogleMapPage;
import org.dorum.automation.utils.WebDriverContainer;
import org.dorum.automation.utils.WebDriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

@Log4j2
public class TestBase {
    protected GoogleMapPage googleMaps;
    protected SoftAssert softAssert;

    @BeforeMethod
    public void beforeMethod(Method method) {
        ThreadContext.put("threadName", String.valueOf(Thread.currentThread().getId()));
        log.info("*****************************************************");
        log.info("Test started {}", method.getName());
        log.info("Method name: {}, Thread ID: {}, Session ID {} is started", method.getName(),
                Thread.currentThread().getId(), ((RemoteWebDriver) WebDriverFactory.createDriver()).getSessionId().toString());
        softAssert = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method method, ITestResult result) {
        if (!result.isSuccess()) {
            googleMaps.takeScreenShot(method.getName() + "-Failed-" + Thread.currentThread().getId());
        }
        log.info("Method name: {}, Thread ID: {}, Session ID {}, is finished", method.getName(),
                Thread.currentThread().getId(), ((RemoteWebDriver) WebDriverContainer.getDriver()).getSessionId().toString());
        WebDriverFactory.quitDriver();
        ThreadContext.clearAll();
    }
}
