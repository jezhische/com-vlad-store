package com.vlad.store.repository;

import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static org.junit.Assert.*;

public class ProductImageRepositoryTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductImageRepository repository;
    @Autowired
    private EntityManager em;

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
//                repository.findProductJoinProductImagesIdByProductNamePart("test"));
                repository.findProductJoinProductImagesIdByProductNamePart());
    }

//    @Test
//    public void test1() {
//        Query nativeQuery = em.createNativeQuery("SELECT * FROM find_product_product_images_id_by_product_name_part('test')");
//        List resultList = nativeQuery.getResultList();
//        System.out.println("*********" + resultList);
////        System.out.println(((ProductJoinProductImageDTO) resultList.get(0)));
//    }
}