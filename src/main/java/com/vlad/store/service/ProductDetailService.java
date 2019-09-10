package com.vlad.store.service;

import com.vlad.store.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductDetailService {

    ProductDetail save(ProductDetail productDetail);

    ProductDetail update(ProductDetail productDetail);

    void delete(ProductDetail productDetail);

    void deleteById(Long id);

    List<ProductDetail> findAllById(Iterable<Long> longs);

    Page<ProductDetail> findAll(Pageable pageable);

    Optional<ProductDetail> findById(Long id);
}
