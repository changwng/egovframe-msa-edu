package org.egovframe.cloud.cmsservice.tus;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ThumbnailExtractor {

    private static final String EXTENSION = "png";
    private static final String DEFAULT_IMAGE_PATH = "src/main/resources/static/images/default-thumbnail.png";

    public static String extract(File source) throws IOException {
        File thumbnail = new File(source.getParent(), source.getName().split("\\.")[0] + "." + EXTENSION);

        try {
            FrameGrab frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(source));

            frameGrab.seekToSecondPrecise(0);

            Picture picture = frameGrab.getNativeFrame();

            BufferedImage bi = AWTUtil.toBufferedImage(picture);

            // 500kb 이하로 image 크기 낮추기
            float width = bi.getWidth();
            float height = bi.getHeight();
            float scale = 1.0f;
            while (width * height * 3 > 500 * 1024) {
                width = width * 0.9f;
                height = height * 0.9f;
                scale = scale * 0.9f;
            }
            BufferedImage resized = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(bi, 0, 0, resized.getWidth(), resized.getHeight(), null);
            g2d.dispose();

            ImageIO.write(resized, EXTENSION, thumbnail);

        } catch (Exception e) {
            File defaultImage = new File(DEFAULT_IMAGE_PATH);

            try {
                FileUtils.copyFile(defaultImage, thumbnail);
            } catch (Exception ex) {
                log.info("Thumbnail Extract Failed => {}", source.getPath(), e);
            }
        }

        return thumbnail.getName();
    }


}
