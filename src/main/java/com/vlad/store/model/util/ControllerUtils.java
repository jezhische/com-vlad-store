package com.vlad.store.model.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ControllerUtils {

// https://stackoverflow.com/questions/1228381/scale-an-image-which-is-stored-as-a-byte-in-java
     public static byte[] scaleImageFromByteArray(byte[] data, String fileType, int desiredHeight)
             throws IOException {
         BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
         int width = (int) ((double) desiredHeight / image.getHeight() * image.getWidth());
         Image scaledInstance = image.getScaledInstance(width, desiredHeight, Image.SCALE_SMOOTH);
         BufferedImage resized = new BufferedImage(width, desiredHeight, BufferedImage.TYPE_INT_RGB);
         resized.getGraphics().drawImage(scaledInstance, 0, 0, new Color(0, 0, 0), null);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ImageIO.write(resized, fileType, baos);
         return baos.toByteArray();
     }
}
