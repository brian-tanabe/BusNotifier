package com.btanabe.busnotifier.utilities.images;

import fr.jcgay.notification.Icon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static java.awt.Font.BOLD;


/**
 * Created by Brian on 12/24/16.
 */
@Slf4j
@RequiredArgsConstructor
public class NotificationIconFactory extends ImageHelper {

    private static final String BASE_IMAGE = "images/arrival-message-base-icon.png";

    // TODO Turn this into an interface to chose platform specific default fonts:
    private static final String FONT_NAME = "Helvetica Neue";

    public URL createMessageIconImage(String routeName) throws IOException {
        BufferedImage bufferedImage = getImageAsBufferedImage(BASE_IMAGE);
        overlayBusRouteNameOnImage(bufferedImage, routeName);

        return getFilesystemUrlForBufferedImage(bufferedImage);
    }

    public Icon createMessageIcon(String routeName) throws IOException {
        BufferedImage bufferedImage = getImageAsBufferedImage(BASE_IMAGE);
        overlayBusRouteNameOnImage(bufferedImage, routeName);

        return Icon.create(createMessageIconImage(routeName), routeName);
    }

    private void overlayBusRouteNameOnImage(BufferedImage image, String routeName) {
        Graphics graphics = image.getGraphics();

        setTextHeight(image.getWidth(), image.getHeight(), graphics, routeName);

        Pair<Integer, Integer> textPlacement = calculateTextPlacement(routeName, image, graphics);

        // Draw the text twice to make it look shadowed:
        graphics.setColor(Color.WHITE);
        graphics.drawString(routeName, textPlacement.getLeft(), textPlacement.getRight());

        graphics.setColor(new Color(255, 204, 0));
        graphics.drawString(routeName, textPlacement.getLeft() + 2, textPlacement.getRight() + 2);
    }

    private void setTextHeight(int imageWidth, int imageHeight, Graphics graphics, String text) {
        int maxTextHeight = (int) ((double) imageHeight / 1.5);
        int maxTextWidth = (int) ((double) imageWidth / 1.5);

        int fontHeight = 48;
        double textHeight, textWidth;
        Font font;
        do {
            fontHeight += 1;
            font = new Font(FONT_NAME, BOLD, fontHeight);
            graphics.setFont(font);
            Rectangle2D stringBounds = graphics.getFontMetrics(font).getStringBounds(text, graphics);
            textHeight = stringBounds.getHeight();
            textWidth = stringBounds.getWidth();
        } while (textHeight < maxTextHeight && textWidth < maxTextWidth);
    }

    private Pair<Integer, Integer> calculateTextPlacement(String text, BufferedImage image, Graphics graphics) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D stringBounds = fontMetrics.getStringBounds(text, graphics);

        final int imageHeight = image.getHeight();
        final int imageWidth = image.getWidth();

        final int textWidth = (int) stringBounds.getWidth();
        final int textHeight = (int) stringBounds.getHeight();

        Integer xPlacement = (imageWidth - textWidth) / 2;
        Integer yPlacement = (imageHeight - textHeight) / 2 + fontMetrics.getAscent();

        return new ImmutablePair<>(xPlacement, yPlacement);
    }
}
