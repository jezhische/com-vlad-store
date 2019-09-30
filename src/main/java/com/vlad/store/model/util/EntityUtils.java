package com.vlad.store.model.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EntityUtils {

    public static MultipartFile convertImgToMultipartFile(File image) throws IOException {
        String fileName = image.getName();
        int threshold = ((int) image.length());
        File parent = image.getParentFile();
        FileItem fileItem = new DiskFileItem("image-to-multipart", "multipart/form-data",
                false, fileName, threshold, parent);
        // to write file content to fileItem:
        IOUtils.copy(new FileInputStream(image), fileItem.getOutputStream());
        return new CommonsMultipartFile(fileItem);
    }

    public static byte[] convertImgFileToByteArray(File image) throws IOException {
//        BufferedImage readBuffered = ImageIO.read(image);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(readBuffered, FilenameUtils.getExtension(image.getPath()), baos);
//        byte[] bytes = baos.toByteArray();
//        baos.flush();
//        return bytes;
        return IOUtils.toByteArray(new FileInputStream(image));
    }

//    public static byte[] convertImgFromURLToByteArray(String imgURL) throws IOException {
////        imgURL = StringUtils.cleanPath(imgURL);
//        URL input = new URL(imgURL);
//        BufferedImage readBuffered = ImageIO.read(input);
//    }
}
