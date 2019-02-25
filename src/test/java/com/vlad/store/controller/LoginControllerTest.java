package com.vlad.store.controller;

import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class LoginControllerTest extends BasePostgresConnectingTest {

    @Autowired
    LoginController loginController;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void hackPassword() {

    }
}