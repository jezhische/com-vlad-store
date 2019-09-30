package com.vlad.store.model;

import com.vlad.store.model.util.EntityUtils;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {

    public ProductImage() {
    }

    public ProductImage(File image) throws IOException {
        fileName = image.getName();
        fileType = FilenameUtils.getExtension(image.getPath());
        data = EntityUtils.convertImgFileToByteArray(image);
    }

    public ProductImage(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileType")
    private String fileType;

    @ToString.Exclude
    @Lob @Basic(fetch=LAZY)
    @Column(name = "data")
    private byte[] data;

    @ToString.Exclude
    @ManyToMany(mappedBy = "productImages") // fetch = FetchType.LAZY by default
    private Set<ProductDetail> productDetails = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImage)) return false;
        return id != null && id.equals(((ProductImage) o).id);
    }

    @Override
    public int hashCode() {
        return 31 * id.intValue();
    }
}
