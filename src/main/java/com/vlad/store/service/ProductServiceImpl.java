package com.vlad.store.service;

import com.vlad.store.model.Product;
import com.vlad.store.repository.ProductDetailRepository;
import com.vlad.store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private ProductDetailRepository detailRepository;

    public ProductServiceImpl(ProductRepository repository, ProductDetailRepository detailRepository) {
        this.repository = repository;
        this.detailRepository = detailRepository;
    }

    @Override
    public Product save(Product product) {
        return repository.saveAndFlush(product);
    }

    @Override
    public Product update(Product product) {
        return repository.saveAndFlush(product);
    }

    @Override
    public void delete(Product product) {
        detailRepository.deleteAllByProductIdReturnCount(product.getId());
        repository.delete(product);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }
}
