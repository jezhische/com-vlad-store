package com.vlad.store.service;


import com.vlad.store.model.Customer;

public interface CustomerService {

    Customer findByNickname(String nickname);

    Customer findById(long id);

    Customer save(Customer customer);

    void deleteByNickname(String nickname);
}
