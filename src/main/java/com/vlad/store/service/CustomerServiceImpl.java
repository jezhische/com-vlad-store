package com.vlad.store.service;

import com.vlad.store.model.Customer;
import com.vlad.store.model.Role;
import com.vlad.store.model.roles.RoleEnum;
import com.vlad.store.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RoleService roleService,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Customer findByLogin(String login) {
        return customerRepository.findByLogin(login);
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        Role customerRole = roleService.findByRole(RoleEnum.CUSTOMER.toString());
//        System.out.println("************************************************************ from customerServiceImpl.save(): customerRole = " + customerRole.getRole());
        customer.setRoles(customerRole);
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteByLogin(String login) {
        customerRepository.deleteByLogin(login);
    }
}
