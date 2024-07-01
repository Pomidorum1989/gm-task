package org.dorum.automation.Pages.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Objects;

@Log4j2
public class WebDriverFactory {
    private static ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    public synchronized static WebDriver getDriver() {
        WebDriverManager webDriverManager;
        if (Objects.isNull(DRIVER_THREAD_LOCAL.get())) {
            switch (Config.getBrowser().toLowerCase()) {
                case "chrome_docker":
                    if (WebDriverManager.isDockerAvailable()) {
                        webDriverManager = WebDriverManager.chromedriver().browserInDocker();
                        webDriverManager.dockerDefaultArgs("--disable-gpu,--no-sandbox,--lang=en-US,--disable-gpu,--incognito");
                        DRIVER_THREAD_LOCAL.set(webDriverManager.create());
                        log.info("Browser container id: {}, docker server url:{}",
                                webDriverManager.getDockerBrowserContainerId(), webDriverManager.getDockerSeleniumServerUrl());
                    }
                    break;
                case "chrome":
                    webDriverManager = WebDriverManager.chromedriver();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--lang=en-US");
//                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--incognito");
                    DRIVER_THREAD_LOCAL.set(webDriverManager.create());
                    log.info("Chrome driver location {}", webDriverManager.getDownloadedDriverPath());
                    log.info("Web driver was created with Thread ID: {}, Session ID: {}",
                            Thread.currentThread().getId(), ((RemoteWebDriver) DRIVER_THREAD_LOCAL.get()).getSessionId());
                    break;
                case "firefox":
                    webDriverManager = WebDriverManager.firefoxdriver();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("intl.accept_languages", "en-US");
                    firefoxOptions.setProfile(profile);
                    firefoxOptions.addArguments("--headless");
                    WebDriver webDriver = webDriverManager.capabilities(firefoxOptions).create();
                    DRIVER_THREAD_LOCAL.set(webDriver);
                    log.info("FireFox driver location {}", webDriverManager.getDownloadedDriverPath());
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    DRIVER_THREAD_LOCAL.set(new EdgeDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser: " + Config.getBrowser());
            }
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    public synchronized static void quitDriver() {
        if (Objects.nonNull(DRIVER_THREAD_LOCAL.get())) {
            log.info("Web driver was removed with Thread ID: {}, Session ID: {}",
                    Thread.currentThread().getId(), ((RemoteWebDriver) DRIVER_THREAD_LOCAL.get()).getSessionId());
            DRIVER_THREAD_LOCAL.get().quit();
            DRIVER_THREAD_LOCAL.remove();
        }
    }
}