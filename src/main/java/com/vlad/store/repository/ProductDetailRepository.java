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
    void delete(ProductDetail productDetail);

    @Override
    void deleteById(Long id);

    @Override
    List<ProductDetail> findAllById(Iterable<Long> longs);

    @Override
    Page<ProductDetail> findAll(Pageable pageable);

    @Override
    Optional<ProductDetail> findById(Long id);
}
