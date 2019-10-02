package com.vlad.store.service;

import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    ProductImage saveFile(MultipartFile file);

    ProductImage saveFile(ProductImage productImage);

    ProductImage updateFile(ProductImage stored, MultipartFile update);

    ProductImage updateFile(ProductImage stored, ProductImage update);

    Optional<ProductImage> findById(Long id);

    List<ProductImage> findAllByProductDetails(Long productDetailId);

//    List<ProductImage> findAllByProductDetailsContainsProductNameOrderByData(ProductDetail detail);

    List<ProductImage> findAll();

    List<ProductImage> findByFileName(String fileName);

    void delete(ProductImage productImage);

    void deleteById(Long id);

    void findAllProductImageIdByProductName();

    List<ProductJoinProductImageDTO> test1test();
}
