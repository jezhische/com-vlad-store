package com.vlad.store.service;

import com.vlad.store.model.ProductDetail;
import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    ProductImage saveFile(MultipartFile file);

    ProductImage updateFile(ProductImage stored, MultipartFile update);

    Optional<ProductImage> findById(Long id);

    List<ProductImage> findAllByProductDetails(Long productDetailId);

    List<ProductImage> findAllByProductDetailsContainsProductNameOrderByData(ProductDetail detail);

    List<ProductImage> findAll();

    Optional<ProductImage> findByFileName(String fileName);

    void delete(ProductImage productImage);

    void deleteById(Long id);
}
