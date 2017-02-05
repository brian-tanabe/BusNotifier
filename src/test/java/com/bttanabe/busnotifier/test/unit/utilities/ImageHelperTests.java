package com.bttanabe.busnotifier.test.unit.utilities;

import com.btanabe.busnotifier.utilities.images.NotificationIconFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import static com.bttanabe.busnotifier.test.utilities.ImageComparator.assertImageEquals;

/**
 * Created by Brian on 12/24/16.
 */
@ContextConfiguration("classpath*:spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ImageHelperTests {
    private final NotificationIconFactory imageHelper = new NotificationIconFactory();

    @Value("classpath:test-images/expected-route11-icon.png")
    private Resource expectedRoute11Image;

    @Value("classpath:test-images/expected-link-icon.png")
    private Resource expectedLinkImage;

    @Test
    public void shouldBeAbleToCreateBusArrivalIconsWithDoubleDigitNumbersCorrectly() throws Exception {
        RenderedImage testImage = ImageIO.read(imageHelper.createMessageIconImage("11"));
        BufferedImage expectedImage = ImageIO.read(expectedRoute11Image.getFile());

        assertImageEquals(testImage, expectedImage);
    }

    @Test
    public void shouldBeAbleToCreateBusArrivalIconsWithFourCharactersCorrectly() throws Exception {
        RenderedImage testImage = ImageIO.read(imageHelper.createMessageIconImage("LINK"));
        BufferedImage expectedImage = ImageIO.read(expectedLinkImage.getFile());

        assertImageEquals(testImage, expectedImage);
    }
}
