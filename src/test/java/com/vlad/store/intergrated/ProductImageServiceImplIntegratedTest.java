package com.vlad.store.intergrated;

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

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProductImageServiceImplIntegratedTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductService productService;

    private ProductImage productImage1;
    private ProductImage productImage2;
//    private ProductDetail productDetail;
    private Product product;

    @Before
    public void setUp() throws Exception {
        productImage1 = new ProductImage(new File("src\\test\\java\\com\\vlad\\store\\testUtils\\map1.jpeg"));
        productImage2 = new ProductImage(new File("src\\test\\java\\com\\vlad\\store\\testUtils\\map2.jpeg"));
    }

    @After
    public void tearDown() throws Exception {
        productImage1 = productImage2 = null;
        product = null;
    }

    /**
     * save one test Product, 5 related test ProductDetails and 2 ProductImage for each to the db,
     * then assert all is implemented right
     * @throws Exception
     */
    @Test
    @Rollback
    public void saveProductWithBatch() throws Exception {
        int pdCount = 5;
        Product prod =
               productService.save(Product.builder().name("testPWB").specification("for testing of productDetail batch").build());
        productImage1 = productImageService.saveFile(productImage1);
        productImage2 = productImageService.saveFile(productImage2);
        for (int i = 0; i < pdCount; i++) {
            // create ProductDetail object
            ProductDetail pd = TestUtil.getProductDetail();
            pd.setProduct(prod);
            productDetailService.save(pd);
//            pd.setProduct(prod);
            pd.addProductImages(productImage1, productImage2);
            assertEquals(pd, productDetailService.findById(pd.getId()).orElseGet(ProductDetail::new));
            assertEquals(i + 1, productDetailService.findAllByProductId(prod.getId()).size());
            pd.getProductImages()
                    .forEach(next -> assertEquals(productImageService.findById(next.getId()).orElseGet(ProductImage::new), next));
        }
        // select ProductJoinProductImageDTO by Product name part
        List<ProductJoinProductImageDTO> productJoinProductImageDTOList = productImageService.selectProductJoinProductImageDTO("test");
        // get ProductImage ids from obtained objects
        List<Long> productImageIdList = productJoinProductImageDTOList.stream()
                .map(ProductJoinProductImageDTO::getProductImageId) // (item -> item.getProductImageId())
                .collect(Collectors.toList());
        assertTrue(productImageIdList.contains(productImage1.getId()));
        assertTrue(productImageIdList.contains(productImage2.getId()));
    }

    @Test
    public void saveFile() {
    }

    @Test
    public void updateFile() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findAllByProductDetails() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByFileName() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }

    // ============================================================================================================ util

//    private ProductDetail getProductDetail() {
//        return ProductDetail.builder()
//                .size(TestUtil.generateRandomSize())
//                .color(TestUtil.generateRandomName())
//                .available(true)
//                .price(TestUtil.generateRandomPrice())
//                .build();
//    }
}