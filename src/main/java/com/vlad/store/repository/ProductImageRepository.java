package com.vlad.store.repository;

import com.vlad.store.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    <S extends ProductImage> S saveAndFlush(S fileModel);

    void delete(ProductImage fileModel);

    void deleteById(Long id);

    Page<ProductImage> findAll(Pageable pageable);

    Optional<ProductImage> findById(Long id);

    List<ProductImage> findAllByProductDetails(Long productDetailId);


//    List<ProductImage> findAllByProductNamePartOrderByData(String productNamePart);

    List<ProductImage> findByFileName(String fileName);


}
