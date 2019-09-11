package com.vlad.store.service;

import com.vlad.store.model.ProductDetail;
import com.vlad.store.repository.ProductDetailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    private ProductDetailRepository repository;

    public ProductDetailServiceImpl(ProductDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return repository.saveAndFlush(productDetail);
    }

    @Override
    public ProductDetail update(ProductDetail productDetail) {
        return repository.saveAndFlush(productDetail);
    }

    @Override
    public void delete(ProductDetail productDetail) {
        repository.delete(productDetail);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProductDetail> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs);
    }

    @Override
    public Page<ProductDetail> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<ProductDetail> findById(Long id) {
        return repository.findById(id);
    }
}
