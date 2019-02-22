package com.vlad.store.service;

import com.vlad.store.model.Customer;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CustomerServiceImplRealDBTest extends BasePostgresConnectingTest {

    @Autowired
    private CustomerService customerService;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = Customer.builder()
                .nickname("iamthefirst")
                .password("password")
                .build();
    }

    @After
    public void tearDown() throws Exception {
        customer = null;
    }

    @Test
    public void findByLogin() {
        Customer byLogin = customerService.findByNickname(customer.getNickname());
        assertEquals(customer.getNickname(), byLogin.getNickname());
    }

    @Test
    public void findById() {
        Customer byLogin = customerService.findByNickname(customer.getNickname());
        Customer byId = customerService.findById(byLogin.getId());
        assertEquals(customer.getNickname(), byId.getNickname());
    }

    @Test
    public void save() {
        Customer saved = customerService.save(customer);
        Customer byId = customerService.findById(saved.getId());
        assertEquals(saved.getNickname(), byId.getNickname());
        assertEquals(saved, byId);
    }

    @Test
    public void loginUniqueConstraint() {
        customerService.save(customer);
    }

    @Test
    public void deleteByLogin() {
        customerService.deleteByNickname(customer.getNickname());
        assertNull(customerService.findByNickname(customer.getNickname()));
    }
}

