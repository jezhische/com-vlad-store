package com.vlad.store.service.productionTest;

import com.vlad.store.model.Product;
import com.vlad.store.model.ProductDetail;
import com.vlad.store.service.ProductDetailService;
import com.vlad.store.service.ProductService;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;
    private Product product;
    private ProductDetail productDetail;

    @Before
    public void setUp() throws Exception {
        product = Product.builder()
                .name("product")
                .specification("good product")
                .build();
        productDetail = ProductDetail.builder()
                .size(25)
                .color("red")
                .available(true)
                .price(BigDecimal.valueOf(286))
                .build();
    }

    @After
    public void tearDown() throws Exception {
        product = null;
        productDetail = null;
    }

    @Test
    @Rollback
    public void saveAndFlush() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
    }

    @Test
    @Rollback
    public void onDeleteOrphansBehavior() throws Exception {
        // create products entry
        Product savedProduct = productService.save(product);
        assertEquals(savedProduct, productService.findById(savedProduct.getId()).orElseGet(Product::new));
        // create a bunch of product_details entries in db
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ProductDetail pd = getProductDetail();
//            pd.setProduct(savedProduct);
            productDetailList.add(productDetailService.save(pd));
        }
        productDetailList.forEach(detail -> {
            System.out.println("--------------------------------------------------------------- productDetail.id = " + detail.getId());
            assertEquals(detail, productDetailService.findById(detail.getId()).orElseGet(ProductDetail::new));
        });

        // add MTO relations to product_details bunch
        productDetailList.forEach(detail -> detail.setProduct(savedProduct));
        productDetailList.forEach(detail -> assertEquals(savedProduct, productDetail.getProduct()));
        // delete saved Product and assert that there are no product_details entries with saved Product relations
//        productService.delete(saved);
//        List<ProductDetail> byId = productDetailService.findAllById(productDetailList.stream()
//                .map(ProductDetail::getId).collect(Collectors.toList())); // map(detail -> detail.getId())
//        assertTrue(byId.isEmpty());
    }

    @Test
    public void findAllById() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findById() throws Exception {
    }

// ============================================================================================================ util

    private ProductDetail getProductDetail() {
        return productDetail = ProductDetail.builder()
                .size(44)
                .color("blue")
                .available(true)
                .price(BigDecimal.valueOf(135))
                .build();
    }
}