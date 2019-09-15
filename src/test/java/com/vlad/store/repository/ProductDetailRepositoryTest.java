package com.vlad.store.repository;

import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ProductDetailRepositoryTest extends BasePostgresConnectingTest {

    @Autowired
    private ProductDetailRepository repository;

    @Test
    public void deleteAllByProductIdReturnCount() {
        int count = repository.deleteAllByProductIdReturnCount(67L);
        System.out.println("----------------------------------------------------------------------------------- " + count);
    }

}