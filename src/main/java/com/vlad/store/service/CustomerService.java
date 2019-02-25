package com.vlad.store.service;


import com.vlad.store.model.Customer;
import com.vlad.store.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerService {

    Customer findByLogin(String login);

    Customer findById(long id);

    Customer save(Customer customer);

    Customer update(Customer customer);

    void deleteByLogin(String login);

//    Optional<Customer> findByRole(String role);

    Set<Customer> findAllByRolesContainingOrderByRoles(Role role);
}
