package com.vlad.store.repository;

import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ProductImageRepositoryTest extends BasePostgresConnectingTest {

    @Autowired
    ProductImageRepository repository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

//    @Test
//    public void findAllByProductDetailsContainsProductNameOrderByData() throws Exception {
//
//    }


    @Test
    public void findDistinctProductImagesIdByProductNamePartOrderByDatetime() throws Exception {
        System.out.println("**********************************************************"
                + repository.findDistinctProductImagesIdByProductNamePartOrderByDatetime("test"));
    }

    @Test
    public void findProductJoinProductImagesIdByProductNamePart() {
        System.out.println("**********************************************************" +
                repository.findProductJoinProductImagesIdByProductNamePart("test"));
//                repository.findProductJoinProductImagesIdByProductNamePart());
    }
}