package com.vlad.store.repository;

import com.vlad.store.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Override
    <S extends ProductDetail> S saveAndFlush(S productDetail);

    @Override
    void delete(ProductDetail productDetail); // Throws: IllegalArgumentException – in case the given entity is null.

    @Override
    void deleteById(Long id); // IllegalArgumentException – in case the given id is null

    @Override
    List<ProductDetail> findAllById(Iterable<Long> longs); // ?

    @Override
    Page<ProductDetail> findAll(Pageable pageable);

    @Override
    Optional<ProductDetail> findById(Long id); // IllegalArgumentException – if id is null

    @Override
    void deleteInBatch(Iterable<ProductDetail> entities);

    @Query(value = "SELECT * FROM find_all_product_details_by_product_id(?1)", nativeQuery = true)
    List<ProductDetail> findAllByProductId(Long productId);

    /**
     * delete all product_details entries by product_id and return a LIST of deleted entries
     * @param productId id of the {@code Product} that is an owner of these {@code ProductDetail}'s (OTM relations)
     * @return {@code List} of deleted entries
     */
    @Query(value = "SELECT * FROM delete_all_product_details_by_product_id_return_deleted(?1)", nativeQuery = true)
    List<ProductDetail> deleteAllByProductIdReturnDeleted(Long productId);

    /**
     * delete all product_details entries by product_id and return a COUNT of deleted entries. <br>
     * NB: not ""SELECT *", but "SELECT count(*)"
     * @param productId id of the {@code Product} that is an owner of these {@code ProductDetail}'s (OTM relations)
     * @return COUNT of deleted entries
     */
// TODO: получаю 1 вместо действительного числа возвращенных записей; исправить??? See: GET DIAGNOSTICS is used to
//  display number of modified/deleted records. https://stackoverflow.com/questions/2251567/how-to-get-the-number-of-deleted-rows-in-postgresql
    @Query(value = "SELECT count(*) FROM delete_all_product_details_by_product_id_return_count(?1)", nativeQuery = true)
    int deleteAllByProductIdReturnCount(Long productId);
}
