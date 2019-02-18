package com.vlad.store.repository;

import com.vlad.store.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByLogin(@NotEmpty(message = "*Please provide your login") String login);

    Customer findById(long id);
}
