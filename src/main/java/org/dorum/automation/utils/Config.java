package org.dorum.automation.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Log4j2
public class Config {
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (Objects.isNull(input)) {
                throw new IOException("Unable to load config.properties");
            }
            PROPERTIES.load(input);
        } catch (IOException ex) {
            log.error("Unable to load config.properties");
        }
    }

    public static String getUrl() {
        return PROPERTIES.getProperty("url");
    }

    public static String getBrowser() {
        return PROPERTIES.getProperty("browser", "chrome");
    }
}
