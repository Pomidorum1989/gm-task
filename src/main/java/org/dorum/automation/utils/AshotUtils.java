package org.dorum.automation.utils;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Log4j2
public class AshotUtils {
    public static boolean compareScreenShots(WebElement webElement, String path, boolean isAshotScreenshot) {
        if (isAshotScreenshot) {
            JQueryUtils.injectJQuery();
            WebDriverWaitUtils.waitJQueryLoad();
            Screenshot ashotScreenshot = new AShot().
//                    coordsProvider(new WebDriverCoordsProvider()).
                    shootingStrategy(ShootingStrategies.viewportPasting(100)).
                    takeScreenshot(WebDriverContainer.getDriver(), webElement);
            return compare(ashotScreenshot.getImage(), path);
        } else {
            File file = webElement.getScreenshotAs(OutputType.FILE);
            BufferedImage screenshot = null;
            try {
                screenshot = ImageIO.read(file);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            return compare(screenshot, path);
        }
    }

    private static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try (InputStream inputStream = AshotUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (Objects.isNull(inputStream)) {
                log.error("Resource not found: {}", path);
            } else {
                image = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            log.error("Unable to load image at path: {}. Error: {}", path, e.getMessage());
        }
        return image;
    }

    private static boolean compare(BufferedImage image, String path) {
        String name = path.replaceAll("images/", "");
        PointsMarkupPolicy diffMarkupPolicy = new PointsMarkupPolicy();
        diffMarkupPolicy.setDiffSizeTrigger(150);
        ImageDiff diff = new ImageDiffer().withDiffMarkupPolicy(diffMarkupPolicy).makeDiff(image, loadImage(path + ".png"));
        try {
            ImageIO.write(image, "png", new File("./target/" + name + "_actual.png"));
            ImageIO.write(diff.getMarkedImage(), "png", new File("./target/" + name + "_diff.png"));
        } catch (IOException e) {
            log.error("Unable to create screenshot\n {}", e.getMessage());
        }
        return diff.hasDiff();
    }
}
