package com.vlad.store.repository;

import com.vlad.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    <S extends Product> S saveAndFlush(S product);

    @Override
    void delete(Product product);

    @Override
    void deleteById(Long id);

    @Override
    List<Product> findAllById(Iterable<Long> longs);

    @Override
    Page<Product> findAll(Pageable pageable);

    @Override
    Optional<Product> findById(Long id);
}
