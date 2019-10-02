package com.vlad.store.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

// https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/
@Data
@Builder

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
