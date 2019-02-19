package com.vlad.store.service;

import com.vlad.store.model.Role;
import com.vlad.store.model.roles.RoleEnum;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RoleServiceImplTest extends BasePostgresConnectingTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void findByRole() {
        Role customer = roleService.findByRole("CUSTOMER");
        assertEquals("CUSTOMER", customer.getRole());
        assertEquals(RoleEnum.CUSTOMER.toString(), customer.getRole());
    }
}