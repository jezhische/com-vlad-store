package com.vlad.store.service;

import com.vlad.store.model.Customer;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

public class CustomerServiceImplRealDBTest extends BasePostgresConnectingTest {

    @Autowired
    private CustomerService customerService;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = Customer.builder()
                .login("iamthefirst")
                .password("password")
                .build();
    }

    @After
    public void tearDown() throws Exception {
        customer = null;
    }

    @Test
    public void findByLogin() {
        Customer byLogin = customerService.findByLogin(customer.getLogin());
        assertEquals(customer.getLogin(), byLogin.getLogin());
    }

    @Test
    public void findById() {
        Customer byLogin = customerService.findByLogin(customer.getLogin());
        Customer byId = customerService.findById(byLogin.getId());
        assertEquals(customer.getLogin(), byId.getLogin());
    }

    @Test
    public void save() {
        Customer saved = customerService.save(customer);
        Customer byId = customerService.findById(saved.getId());
        assertEquals(saved.getLogin(), byId.getLogin());
        assertEquals(saved, byId);
    }

    @Test
    public void loginUniqueConstraint() {
        customerService.save(customer);
    }

    @Test
    public void deleteByLogin() {
        customerService.deleteByLogin(customer.getLogin());
        assertNull(customerService.findByLogin(customer.getLogin()));
    }
}

