package com.vlad.store.service.productionTest;

import com.vlad.store.model.ProductDetail;
import com.vlad.store.service.ProductDetailService;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ProductDetailServiceImplTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductDetailService service;

    private ProductDetail productDetail;

    @Before
    public void setUp() throws Exception {
        productDetail = ProductDetail.builder()
                .size(25)
                .color("red")
                .available(true)
                .price(BigDecimal.valueOf(286))
                .build();
    }

    @After
    public void tearDown() throws Exception {
        productDetail = null;
    }

    @Test
    @Rollback
    public void save() throws Exception {
        ProductDetail save = service.save(productDetail);
        assertEquals(save, service.findById(save.getId()).orElseGet(null));
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

}