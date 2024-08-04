package org.dorum.automation.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.JavascriptExecutor;

@Log4j2
public class JQueryUtils {
    public static void injectJQuery() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverContainer.getDriver();
        if (!(Boolean) js.executeScript("return (typeof jQuery != \"undefined\")")) {
            js.executeScript(
                    "var headID = document.getElementsByTagName('head')[0]; " +
                            "var newScript = document.createElement('script'); " +
                            "newScript.type = 'text/javascript'; " +
                            "newScript.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js'; " +
                            "headID.appendChild(newScript);");
        }
        log.info("JQuery need to be loaded");
    }
}
