package com.vlad.store.blobTest;

import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class Test extends BasePostgresConnectingTest {

    @Autowired
    private BlobTestRepository repository;

    @org.junit.Test
    public void testSave() {
       assertTrue(repository.findAll().isEmpty());

    }
}
