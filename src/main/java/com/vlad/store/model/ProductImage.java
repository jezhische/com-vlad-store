package com.vlad.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileType")
    private String fileType;

    @Lob @Basic(fetch=LAZY)
    @Column(name = "data")
    private byte[] data;

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
