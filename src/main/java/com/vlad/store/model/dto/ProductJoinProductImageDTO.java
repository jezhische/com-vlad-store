package com.vlad.store.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Data
@Builder
@SqlResultSetMapping(
        name = "product_pimage",
        classes = {@ConstructorResult(
                targetClass = ProductJoinProductImageDTO.class,
                columns = {
                             @ColumnResult(name = "p_name", type = Long.class)
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
