package com.vlad.store.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

// https://stackoverflow.com/questions/46171583/jpa-data-repositories-with-sqlresultsetmapping-and-native-queries
// https://www.baeldung.com/jpa-sql-resultset-mapping
@Data
@Builder
//@Component
//@Entity
@SqlResultSetMapping(
        name = "join_result_find_product_product_images_id_by_product_name_part",
        classes = {@ConstructorResult(
                targetClass = ProductJoinProductImageDTO.class,
                columns = {
                        @ColumnResult(name = "p_name", type = Long.class),
                        @ColumnResult(name = "p_producer_id", type = String.class),
                        @ColumnResult(name = "pimage_id", type = Long.class),
                        @ColumnResult(name = "pimage_name", type = String.class),
                        @ColumnResult(name = "p_producer_id", type = String.class),
                        @ColumnResult(name = "pimage_data", type = Integer.class)
                }
        )
        }
)
//p_id BIGINT,
//        p_name VARCHAR,
//        p_producer_id BIGINT,
//        pimage_id BIGINT,
//        pimage_name VARCHAR,
//        pimage_data OID

// see ProductImageRepository.class
//@NamedNativeQuery(name = "join_result_find_product_product_images_id_by_product_name_part",
//        resultClass = ProductJoinProductImageDTO.class,
////        resultSetMapping = "join_result_find_product_product_images_id_by_product_name_part",
//        query = "SELECT * FROM find_product_product_images_id_by_product_name_part(?1)")
public class ProductJoinProductImageDTO {

    public ProductJoinProductImageDTO(Long productId, String productName, Long producerId,
                                      Long productImageId, String productImageName, Integer productImageDataOID) {
        this.productId = productId;
        this.productName = productName;
        this.producerId = producerId;
        this.productImageId = productImageId;
        this.productImageName = productImageName;
        this.productImageDataOID = productImageDataOID;
    }

    private Long productId;
    private String productName;
    private Long producerId;
    private Long productImageId;
    private String productImageName;
    private Integer productImageDataOID;
}
