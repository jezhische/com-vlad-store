package service;


import com.vlad.store.model.Customer;

public interface CustomerService {

    Customer findByLogin(String login);

    Customer findById(long id);

    Customer save(Customer customer);
}
