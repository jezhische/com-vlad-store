package com.vlad.store.model.constants;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoleEnumTest {

    @Test
    public void stringEq() {
        assertEquals("ADMIN", RoleEnum.ADMIN.toString());
    }
}