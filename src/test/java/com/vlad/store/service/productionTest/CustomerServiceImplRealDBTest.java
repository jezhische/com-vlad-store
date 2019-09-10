package com.vlad.store.service.productionTest;

import com.sun.org.apache.regexp.internal.RE;
import com.vlad.store.model.Customer;
import com.vlad.store.model.Role;
import com.vlad.store.model.constants.RoleEnum;
import com.vlad.store.service.CustomerService;
import com.vlad.store.service.RoleService;
import com.vlad.store.testConfig.BasePostgresConnectingTest;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.junit.Assert.*;

public class CustomerServiceImplRealDBTest extends BasePostgresConnectingTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = Customer.builder()
                .login("iamthefirst") // iamthesecond
                .password("password")
                .build();
    }
// ====================================================================================================================

    @After
    public void tearDown() throws Exception {
        customer = null;
    }

    @Test
    public void save() {
        Customer saved = customerService.save(customer);
        Customer byId = customerService.findById(saved.getId());
        assertEquals(saved, byId);
        assertThat(customerService.findByLogin(customer.getLogin()).isEnabled(), Matchers.comparesEqualTo(true));
        assertThat(customerService.findByLogin(customer.getLogin()).getRoles().stream().findFirst().get().getRole(),
                Matchers.is("CUSTOMER"));
    }


// =====================================================================================================================
// =============================================================================================================== bunch


    @Rollback
    @Test(expected = DataIntegrityViolationException.class)
    public void loginUniqueConstraint() {
        customerService.save(customer);
    }

    @Test
    public void addRole() {
        customerService.findByLogin(customer.getLogin()).addRoles(roleService.findByRole(RoleEnum.ADMIN.toString()));
        assertTrue(customerService.findByLogin(customer.getLogin()).getRoles().contains(
                roleService.findByRole(RoleEnum.ADMIN.toString())
        ));
    }

    @Test
    public void findByLogin() {
        Customer byLogin = customerService.findByLogin(customer.getLogin());
        assertEquals(customer.getLogin(), byLogin.getLogin());
        System.out.println("************************************" + byLogin);
    }

    @Test
    public void findById() {
        Customer byLogin = customerService.findByLogin(customer.getLogin());
        Customer byId = customerService.findById(byLogin.getId());
        assertEquals(customer.getLogin(), byId.getLogin());
    }

    @Test
    public void findAllByRolesContainingOrderByRoles() {
        Set<Customer> customers = customerService.findAllByRolesContainingOrderByRoles(roleService
                .findByRole(RoleEnum.CUSTOMER.toString()));
        assertTrue(customers.contains(customerService.findByLogin(customer.getLogin())));
        System.out.println("******************************************************" + customers + "**************************");
    }

    @Test
    public void putWithEnabledFalse() {
        Customer byLogin = customerService.findByLogin(customer.getLogin());
        byLogin.setEnabled(false);
        customerService.update(byLogin);
        assertFalse(byLogin.isEnabled());
    }
// =========================================================================================================== end bunch
// =====================================================================================================================



    @Test
    public void deleteByLogin() {
        assertNotNull(customerService.findByLogin(customer.getLogin()));
        Set<Customer> customers = customerService.findAllByRolesContainingOrderByRoles(roleService.findByRole(RoleEnum.CUSTOMER.toString()));
        assertTrue(customers.contains(customerService.findByLogin(customer.getLogin())));
        customerService.deleteByLogin(customer.getLogin());
        assertNull(customerService.findByLogin(customer.getLogin()));
        customers = customerService.findAllByRolesContainingOrderByRoles(roleService.findByRole(RoleEnum.CUSTOMER.toString()));
        customers.addAll(customerService.findAllByRolesContainingOrderByRoles(roleService.findByRole(RoleEnum.ADMIN.toString())));
        assertTrue(!customers.contains(customerService.findByLogin(customer.getLogin())));
    }

// =====================================================================================================================
// ======================================================================================================== special

    @Test
    public void deleteCertainCustomer() {
        String certainLogin = "jezhische";
        assertNotNull(customerService.findByLogin(certainLogin));
        customerService.deleteByLogin(certainLogin);
        assertNull(customerService.findByLogin(certainLogin));
    }

    @Test
    public void putCertainCustomerEnabledTrue() {
        Customer jezhische = customerService.findByLogin("jezhische");
        jezhische.setEnabled(true);
        customerService.update(jezhische);
        assertTrue(customerService.findByLogin(jezhische.getLogin()).isEnabled());
    }

    @Test
    public void comparePassword() {
        Customer jezhische = customerService.findByLogin("jezhische");
        System.out.println("**********************************************" +
                bCryptPasswordEncoder.matches("password", jezhische.getPassword()));
    }

    @Test
    public void setAdminAuthority() {
        Customer jezhische = customerService.findByLogin("jezhische");
        Role admin = roleService.findByRole(RoleEnum.ADMIN.toString());
        Role customer = roleService.findByRole(RoleEnum.CUSTOMER.toString());
        jezhische.addRoles(customer);
        if (!jezhische.getRoles().contains(admin)) {
            System.out.println("****************************************************** set the ADMIN role");
            jezhische.addRoles(admin);
        } else             System.out.println("************************************************ the ADMIN role exists");
        assertTrue(customerService.findByLogin("jezhische").getRoles().contains(admin));
    }

    @Test
    public void deleteAdminAuthority() {
        Customer jezhische = customerService.findByLogin("jezhische");
        Role admin = roleService.findByRole(RoleEnum.ADMIN.toString());
        if (jezhische.getRoles().contains(admin)) {
            System.out.println("****************************************************** delete the ADMIN role");
            customerService.deleteRole(jezhische, RoleEnum.ADMIN.toString());
        }
        assertTrue(!customerService.findByLogin("jezhische").getRoles().contains(admin));
    }
}

