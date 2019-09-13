package com.vlad.store.repository;

import com.vlad.store.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Override
    <S extends ProductDetail> S saveAndFlush(S productDetail);

    @Override
    void delete(ProductDetail productDetail); // Throws: IllegalArgumentException – in case the given entity is null.

    @Override
    void deleteById(Long id); // IllegalArgumentException – in case the given id is null

    @Override
    List<ProductDetail> findAllById(Iterable<Long> longs); // ?

    @Override
    Page<ProductDetail> findAll(Pageable pageable);

    @Override
    Optional<ProductDetail> findById(Long id); // IllegalArgumentException – if id is null

//    @Query findAllByProduct
}
