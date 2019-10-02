package com.vlad.store.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

// https://stackoverflow.com/questions/46171583/jpa-data-repositories-with-sqlresultsetmapping-and-native-queries
// https://www.baeldung.com/jpa-sql-resultset-mapping
@Data
@Builder
//@SqlResultSetMapping(
//        name = "SelectProductJoinProductImageDTO",
//        classes = {@ConstructorResult(
//                targetClass = ProductJoinProductImageDTO.class,
//                columns = {
//                        @ColumnResult(name = "pId", type = Long.class),
//                        @ColumnResult(name = "pName", type = String.class),
//                        @ColumnResult(name = "pPrId", type = Long.class),
//                        @ColumnResult(name = "pImgId", type = Long.class),
//                        @ColumnResult(name = "pImgName", type = String.class),
//                        @ColumnResult(name = "pImgData", type = Integer.class)
//                }
//        )
//        }
//)
////        p_id BIGINT,
////        p_name VARCHAR,
////        p_producer_id BIGINT,
////        pimage_id BIGINT,
////        pimage_name VARCHAR,
////        pimage_data OID
//
//// see ProductImageRepository.class
//@NamedNativeQuery(name = "SelectProductJoinProductImageDTO",
////        resultClass = ProductJoinProductImageDTO.class,
////        query = "SELECT * FROM find_product_product_images_id_by_product_name_part(?1)")
//        query = "SELECT tst.p_id AS pId, tst.p_name AS pName, tst.p_producer_id AS pPrId, tst.pimage_id AS pImgId, tst.pimage_name AS pImgName, tst.pimage_data AS pImgData FROM test1() tst",
//        resultSetMapping = "SelectProductJoinProductImageDTO")
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
