package com.vlad.store.service;

import com.vlad.store.model.ProductImage;

import java.util.List;
import java.util.Optional;

public interface FileUploadModelService {

    ProductImage saveFile(ProductImage productImage);

    ProductImage updateFile(ProductImage productImage);

    Optional<ProductImage> findById(Long id);

    List<ProductImage> findAllByProductId(Long productId);

    void delete(ProductImage fileModel);

    void deleteById(Long id);
}
