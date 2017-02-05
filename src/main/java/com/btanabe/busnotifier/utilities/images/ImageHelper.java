package com.btanabe.busnotifier.utilities.images;

import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Brian on 2/4/17.
 */
public class ImageHelper {

    protected BufferedImage getImageAsBufferedImage(@NonNull final String imagePath) throws IOException {
        InputStream imageInputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
        return ImageIO.read(imageInputStream);
    }

    protected URL getFilesystemUrlForBufferedImage(BufferedImage bufferedImage) throws IOException {
        File outputImageFile = File.createTempFile(UUID.randomUUID().toString(), "png");
        ImageIO.write(bufferedImage, "png", outputImageFile);
        return outputImageFile.toURI().toURL();
    }
}
