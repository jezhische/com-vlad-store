package com.vlad.store.service.productionTest;

import com.vlad.store.model.Product;
import com.vlad.store.model.ProductDetail;
import com.vlad.store.service.ProductDetailService;
import com.vlad.store.service.ProductService;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import com.vlad.store.testUtils.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProductDetailServiceImplTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductDetailService service;

    @Autowired
    private ProductService productService;

    private ProductDetail productDetail;
    private Product product;

    @Before
    public void setUp() throws Exception {
        productDetail = ProductDetail.builder()
                .size(25)
                .color("red")
                .available(true)
                .price(BigDecimal.valueOf(286))
                .build();
        product = Product.builder()
                .name("product")
                .specification("good product")
                .build();
    }

    @After
    public void tearDown() throws Exception {
        productDetail = null;
        product = null;
    }

    @Test
    @Rollback
    public void save() throws Exception {
        ProductDetail save = service.save(productDetail);
        assertEquals(save, service.findById(save.getId()).orElseGet(ProductDetail::new));
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
    public void findAllById() throws Exception {

//        service.findAllById(Arrays.asList())
    }

    @Test
    public void findAll() throws Exception {
//        service.findAll(Pageable.)
    }

    @Test
    public void findById() throws Exception {
    }

    @Test
    @Rollback
    public void findAllByProductId() {
        // create some Product in db
        Product testProduct = new Product();
        BeanUtils.copyProperties(product, testProduct);
        productService.save(testProduct);
        // save several ProductDetails to db and add them to new List
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ProductDetail pd = getProductDetail();
            pd.setProduct(testProduct);
            productDetailList.add(service.save(pd));
        }
        // obtain all the saved ProductDetails from db and compare them with existing list
        ArrayList<ProductDetail> returned = new ArrayList<>(service.findAllByProductId(testProduct.getId()));
        productDetailList.forEach(detail -> {
            System.out.println("--------------------------------------------------------------- productDetail.id = " + detail.getId());
            assertTrue(returned.contains(detail));
        });
    }

    @Test
    @Rollback
    public void deleteAllByProductIdReturnL() throws Exception {
        // create some Product in db
        Product testProduct = new Product();
        BeanUtils.copyProperties(product, testProduct);
        productService.save(testProduct);
        // save several ProductDetails to db and add them to new List
        int listSize = 5;
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            ProductDetail pd = getProductDetail();
            pd.setProduct(testProduct);
            productDetailList.add(service.save(pd));
        }
        // assert that saved ProductDetails exist in db
        assertEquals(productDetailList.size(), listSize);
        productDetailList.forEach(detail -> {
            System.out.println("--------------------------------------------------------------- productDetail.id = " + detail.getId());
            assertEquals(detail, service.findById(detail.getId()).orElseGet(ProductDetail::new));
        });
        // delete all the above ProductDetails and assert they don't exist in db
        List<ProductDetail> deleted = service.deleteAllByProductIdReturnDeleted(testProduct.getId());
        assertTrue(service.findAllById(productDetailList
                .stream().map(ProductDetail::getId).collect(Collectors.toList()))
                .isEmpty());
        // assert that returned list of deleted ProductDetail contains all the deleted entities
        deleted.forEach(detail -> {
            System.out.println("--------------------------------------------------------------- deletedProductDetail.id = " + detail.getId());
            assertTrue(productDetailList.contains(detail));
        });
    }



    @Test
    @Rollback
    public void deleteAllByProductIdReturnCount() throws Exception {
        // create some Product in db
        Product testProduct = new Product();
        BeanUtils.copyProperties(product, testProduct);
        productService.save(testProduct);
        // save several ProductDetails to db and add them to new List
        int listSize = 5;
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            ProductDetail pd = getProductDetail();
            pd.setProduct(testProduct);
            productDetailList.add(service.save(pd));
        }
        // assert that saved ProductDetails exist in db
        assertEquals(listSize, productDetailList.size());
        productDetailList.forEach(detail -> {
            System.out.println("--------------------------------------------------------------- productDetail.id = " + detail.getId());
            assertEquals(detail, service.findById(detail.getId()).orElseGet(ProductDetail::new));
        });
        // delete all the above ProductDetails and assert they don't exist in db
        int count = service.deleteAllByProductIdReturnCount(testProduct.getId());
        assertTrue(service.findAllById(
                productDetailList.stream().map(ProductDetail::getId).collect(Collectors.toList())
        ).isEmpty());
        // assert that returned count of deleted ProductDetail equals the size of the list of saved and deleted entities
        assertEquals(listSize, count);
    }

// ============================================================================================================ util

    private ProductDetail getProductDetail() {
        return productDetail = ProductDetail.builder()
                .size(44)
                .color(TestUtil.generateRandomName())
                .available(true)
                .price(BigDecimal.valueOf(135))
                .build();
    }
}