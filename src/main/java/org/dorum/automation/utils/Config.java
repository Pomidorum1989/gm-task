package org.dorum.automation.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class Config {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to load config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            log.error("Unable to load config file");
        }
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }
}
