package com.vlad.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileDownloadUri;
}
