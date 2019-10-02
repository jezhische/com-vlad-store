package com.vlad.store.repository;

import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
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


//    List<ProductImage> findAllByProductNamePartOrderByDate(String productNamePart);

    List<ProductImage> findByFileName(String fileName);

    @Query(value = "SELECT * FROM find_distinct_product_images_id_by_product_name_part_order_by_datetime(?1)", nativeQuery = true)
    List<Long> findDistinctProductImagesIdByProductNamePartOrderByDatetime(String productNamePart);

// see @NamedNativeQuery in the ProductJoinProductImageDTO.class
    @Query(name = "join_result_find_product_product_images_id_by_product_name_part",
            value = "SELECT * FROM find_product_product_images_id_by_product_name_part(?1)",
//            value = "SELECT * FROM test1()",
            nativeQuery = true)
    List<ProductJoinProductImageDTO> findProductJoinProductImagesIdByProductNamePart(/*@Param("param1")*/ String productNamePart);
//    List<ProductJoinProductImageDTO> findProductJoinProductImagesIdByProductNamePart();


}
