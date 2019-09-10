package com.vlad.store.service;

import com.vlad.store.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ProductDetailServiceImpl implements ProductDetailService {
    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return null;
    }

    @Override
    public ProductDetail update(ProductDetail productDetail) {
        return null;
    }

    @Override
    public void delete(ProductDetail productDetail) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ProductDetail> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Page<ProductDetail> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<ProductDetail> findById(Long id) {
        return Optional.empty();
    }
}
