package com.vlad.store.repository;

import com.vlad.store.model.ProductImage;
import com.vlad.store.model.dto.ProductJoinProductImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    List<ProductImage> findByFileName(String fileName);

    @Query(value = "SELECT * FROM find_distinct_product_images_id_by_product_name_part_order_by_datetime(?1)", nativeQuery = true)
    List<Long> findDistinctProductImagesIdByProductNamePartOrderByDatetime(String productNamePart);

    /**
     * returns result of the NamedNativeQuery "{@code ProductImage.selectProductJoinProductImageDTO}" defined
     * in the {@link ProductImage} class
     * @param productNamePart a part or a whole name of the product
     * @return list of the query results as {@link ProductJoinProductImageDTO} objects
     * @see <a href="https://stackoverflow.com/questions/49056084/got-different-size-of-tuples-and-aliases-exception-after-spring-boot-2-0-0-rel">
     * stackoverflow a "HibernateException: Got different size of tuples and aliases" issue resolving</a>
     */
    @Query(nativeQuery = true)
    List<ProductJoinProductImageDTO> selectProductJoinProductImageDTO(@Param("product_name_part") String productNamePart);



}
