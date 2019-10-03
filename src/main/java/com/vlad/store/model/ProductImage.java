package com.vlad.store.model;

import com.vlad.store.model.dto.ProductJoinProductImageDTO;
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

/**
 * @see <a href="https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/">
 *          vladmihalcea projection query tutorial</a><br>
 * @see <a href="https://stackoverflow.com/questions/49056084/got-different-size-of-tuples-and-aliases-exception-after-spring-boot-2-0-0-rel">
 *     stackoverflow correct code for @SqlResultSetMapping and a "HibernateException: Got different size of tuples
 *     and aliases" issue resolving</a>
 */
@Data
@Entity
@Table(name = "product_images")
@SqlResultSetMapping(
        name = "ProductImage.selectProductJoinProductImageDTO",
        classes = {@ConstructorResult(
                targetClass = ProductJoinProductImageDTO.class,
                columns = {
                        @ColumnResult(name = "pId", type = Long.class),
                        @ColumnResult(name = "pName", type = String.class),
                        @ColumnResult(name = "pPrId", type = Long.class),
                        @ColumnResult(name = "pImgId", type = Long.class),
                        @ColumnResult(name = "pImgName", type = String.class),
                        @ColumnResult(name = "pImgData", type = Integer.class)
                }
        )}
)
// NB: as it's a JPQL, I must to define class for method as "ProductImage.selectPr....DTO"
// NB: parameter must be defined as ":product_name_part" and annotated in the repository method as @Param("product_name_part")
@NamedNativeQueries({
        @NamedNativeQuery(name = "ProductImage.selectProductJoinProductImageDTO",
        query = "SELECT tst.p_id AS pId, tst.p_name AS pName, tst.p_producer_id AS pPrId, " +
                "tst.pimage_id AS pImgId, tst.pimage_name AS pImgName, tst.pimage_data AS pImgData " +
                "FROM find_product_product_images_id_by_product_name_part(:product_name_part) tst",
        resultSetMapping = "ProductImage.selectProductJoinProductImageDTO")
})
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
