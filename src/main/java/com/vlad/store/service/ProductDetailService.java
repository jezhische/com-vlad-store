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

    List<ProductDetail> findAllByProductId(Long productId);

    /**
     * delete all product_details entries by product_id and return a COUNT of deleted entries
     * @param productId id of the {@code Product} that is an owner of these {@code ProductDetail}'s (OTM relations)
     * @return COUNT of deleted entries
     */
    List<ProductDetail> deleteAllByProductIdReturnDeleted(Long productId);

    /**
     * delete all product_details entries by product_id and return a COUNT of deleted entries
     * @param productId id of the {@code Product} that is an owner of these {@code ProductDetail}'s (OTM relations)
     * @return COUNT of deleted entries
     */
    int deleteAllByProductIdReturnCount(Long productId);
}
