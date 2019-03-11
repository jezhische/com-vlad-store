package com.vlad.store.service;

import com.vlad.store.model.ProductImage;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class ProductImageServiceImplTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductImageService productImageService;
    private MultipartFile multipartFile;
    private MultipartFile update_multipartFile;
    private File file;
    private File update;
    public static final String FILENAME = "12.jpeg";
    public static final String UPDATE_FILENAME = "13.jpeg";

    @Before
    public void setUp() throws Exception {
        file = new File("src\\test\\java\\com\\vlad\\store\\testUtils\\" + FILENAME);
        update = new File("src\\test\\java\\com\\vlad\\store\\testUtils\\" + UPDATE_FILENAME);
        System.out.println("******************************************************* file: " + file.getName() +
                ", parent: " + file.getParent());
        System.out.println("******************************************************* update: " + update.getName() +
                ", parent: " + update.getParent());
        String fileName = file.getName();
        int threshold = ((int) file.length());
        File repository = file.getParentFile();

        FileItem fileItem = new DiskFileItem("image-to-store", "multipart/form-data",
                false, fileName, threshold, repository);
        // to write file content to fileItem:
        IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
        multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println("*********************** multipartFile.getBytes().length = " + multipartFile.getBytes().length);

        fileName = update.getName();
        threshold = ((int) update.length());
        repository = update.getParentFile();

        fileItem = new DiskFileItem("image-to-update", "multipart/form-data",
                false, fileName, threshold, repository);
        // to write file content to fileItem:
        IOUtils.copy(new FileInputStream(update), fileItem.getOutputStream());
        update_multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println("*********************** update_multipartFile.getBytes().length = " + update_multipartFile.getSize());
    }

    @After
    public void tearDown() throws Exception {
        multipartFile = null;
        file = null;
        update_multipartFile = null;
        update = null;
    }


    @Test
    public void saveFile() throws IOException {
        productImageService.saveFile(multipartFile);
        ProductImage returned = productImageService.findByFileName(FILENAME).get();
        assertNotNull(returned);
        assertEquals(Files.readAllBytes(file.toPath()).length, returned.getData().length);
    }

    @Test
    public void updateFile() throws IOException {
        if (!productImageService.findByFileName(FILENAME).isPresent()) productImageService.saveFile(multipartFile);
        ProductImage returned = productImageService.findByFileName(FILENAME).get();
        assertNotNull(returned);
        Long fileId = returned.getId();
        assertEquals(Files.readAllBytes(file.toPath()).length, returned.getData().length);

        productImageService.updateFile(returned, update_multipartFile);
        returned = productImageService.findByFileName(UPDATE_FILENAME).get();
        assertNotNull(returned);
        Long updateId = returned.getId();
        assertEquals(Files.readAllBytes(update.toPath()).length, returned.getData().length);
        assertEquals(fileId, updateId);
    }

    @Test
    public void findById() {
    }

    @Test
    public void findAllByProductDetails() {
    }

    @Test
    public void delete() {
        ProductImage returned = productImageService.findByFileName(FILENAME)
                .orElse(productImageService.findByFileName(UPDATE_FILENAME)
                .orElseThrow(() -> new RuntimeException("*********************** File Not Found")));
        productImageService.delete(returned);
        assertFalse(productImageService.findByFileName(FILENAME).isPresent());
        assertFalse(productImageService.findByFileName(UPDATE_FILENAME).isPresent());
    }

    @Test
    public void deleteById() {
    }
}