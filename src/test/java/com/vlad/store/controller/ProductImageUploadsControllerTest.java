package com.vlad.store.controller;

import com.vlad.store.model.Product;
import com.vlad.store.model.ProductDetail;
import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import com.vlad.store.service.ProductDetailService;
import com.vlad.store.service.ProductImageService;
import com.vlad.store.service.ProductService;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import com.vlad.store.testUtils.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProductImageUploadsControllerTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductImageUploadsController controller;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductService productService;
    private ProductImage productImage1;
    private ProductImage productImage2;

    @Before
    public void setUp() throws Exception {
        productImage1 = new ProductImage(new File("src\\test\\java\\com\\vlad\\store\\testUtils\\map1.jpeg"));
        productImage2 = new ProductImage(new File("src\\test\\java\\com\\vlad\\store\\testUtils\\map2.jpeg"));
    }

    @After
    public void tearDown() throws Exception {
        productImage1 = null;
        productImage2 = null;
    }

    @Test
    @Rollback
    public void addProductImageData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int pdCount = 5;
        Product prod =
                productService.save(Product.builder().name("testAddProductImageData")
                        .specification("for ProductImageUploadsController addProductImageData() testing").build());
        productImage1 = productImageService.saveFile(productImage1);
        productImage2 = productImageService.saveFile(productImage2);

        for (int i = 0; i < pdCount; i++) {
            // create ProductDetail object
            ProductDetail pd = TestUtil.getProductDetail();
            pd.setProduct(prod);
            productDetailService.save(pd);
            pd.addProductImages(productImage1, productImage2);
        }
        // select ProductJoinProductImageDTO by Product name part
        List<ProductJoinProductImageDTO> productJoinProductImageDTOList = productImageService.selectProductJoinProductImageDTO("test");
//        // get ProductImage ids from obtained objects
//        List<Long> productImageIdList = productJoinProductImageDTOList.stream()
//                .map(ProductJoinProductImageDTO::getProductImageId) // (item -> item.getProductImageId())
//                .collect(Collectors.toList());
        assertNotEquals(0, productJoinProductImageDTOList.size());
        ProductJoinProductImageDTO any = productJoinProductImageDTOList.stream().findAny().get();
        ProductImage imageById = productImageService.findById(any.getProductImageId()).orElse(new ProductImage());
        // get private controller method with java.reflection
        Method addProductImageData = controller.getClass()
                .getDeclaredMethod("addProductImageData", ProductJoinProductImageDTO.class, HttpServletRequest.class);
        addProductImageData.setAccessible(true);
        Object invoke = addProductImageData.invoke(controller, any, null);
        assertEquals(invoke.getClass(), ProductJoinProductImageDTO.class);
        assertNotEquals(0, ((ProductJoinProductImageDTO) invoke).getProductImageData().length);
        // todo: NB: condition can be false because of the original image height can be less or equals 200px,
        //  so it can be less or equals height of the scaled image
        assertTrue(imageById.getData().length > ((ProductJoinProductImageDTO) invoke).getProductImageData().length);

        System.out.println("************************************************************* imageById length: " + imageById.getData().length);
        System.out.println("************************************************************* invoke length: " + ((ProductJoinProductImageDTO) invoke).getProductImageData().length);

    }

    @Test
    public void testPrimitive() throws Exception {
        System.out.println((double) (200 / 629) * 558);
        System.out.println(((double)200 / 629) * 558);
        System.out.println((double)200 / 629 * 558);
    }
}