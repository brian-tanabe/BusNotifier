package com.bttanabe.busnotifier.test.utilities;

import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/26/16.
 */
public class ImageComparator {

    /**
     * This helper method inspects two imags and asserts that their content is the same
     *
     * @param testImage
     * @param expectedImage
     */
    public static void assertImageEquals(@NonNull RenderedImage testImage, @NonNull BufferedImage expectedImage) {
        DataBuffer testImageDataBuffer = testImage.getData().getDataBuffer();
        DataBuffer expectedImageDataBuffer = expectedImage.getData().getDataBuffer();

        assertThat(testImageDataBuffer.getSize(), is(equalTo(expectedImageDataBuffer.getSize())));

        for (int index = 0; index < testImageDataBuffer.getSize(); index++) {
            assertThat(testImageDataBuffer.getElem(index), is(equalTo(expectedImageDataBuffer.getElem(index))));
        }
    }
}
