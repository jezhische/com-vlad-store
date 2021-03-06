package com.vlad.store.model.dto;

import com.vlad.store.model.ProductImage;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Arrays;

/**
 * see {@link ProductImage} for query and result set mapping
 * @see <a href="https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/">
 *          https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/
 *     </a>
 */
@Data
@Builder
public class ProductJoinProductImageDTO {

    public ProductJoinProductImageDTO(Long productId, String productName, Long producerId, Long productImageId,
                                      String productImageName, byte[] productImageData, String fileType) {
        this.productId = productId;
        this.productName = productName;
        this.producerId = producerId;
        this.productImageId = productImageId;
        this.productImageName = productImageName;
        this.productImageData = productImageData;
        this.fileType = fileType;
    }

    public ProductJoinProductImageDTO(Long productId, String productName, Long producerId,
                                      Long productImageId, String productImageName) {
        this.productId = productId;
        this.productName = productName;
        this.producerId = producerId;
        this.productImageId = productImageId;
        this.productImageName = productImageName;
    }

    private Long productId;
    private String productName;
    private Long producerId;
    private Long productImageId;
    private String productImageName;
    private byte[] productImageData;
    private String fileType;

    @Override
    public String toString() {
        return "ProductJoinProductImageDTO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", producerId=" + producerId +
                ", productImageId=" + productImageId +
                ", productImageName='" + productImageName + '\'' +
                ", productImageData length= " + productImageData.length;
    }

    //    public ProductJoinProductImageDTO(Long productId, String productName, Long producerId,
//                                      Long productImageId, String productImageName, Integer productImageDataOID) {
//        this.productId = productId;
//        this.productName = productName;
//        this.producerId = producerId;
//        this.productImageId = productImageId;
//        this.productImageName = productImageName;
//        this.productImageDataOID = productImageDataOID;
//    }
//
//    private Long productId;
//    private String productName;
//    private Long producerId;
//    private Long productImageId;
//    private String productImageName;
//    private Integer productImageDataOID;
}
