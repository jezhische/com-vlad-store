package com.vlad.store.service;

import com.vlad.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveAndFlush(Product product);

    Product update(Product product);

    void delete(Product product);

    void deleteById(Long id);

    List<Product> findAllById(Iterable<Long> longs);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);
}
