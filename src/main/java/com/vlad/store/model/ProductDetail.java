package com.vlad.store.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


/**
 * NB: the following relations exists but didn't be reflected in this entity to avoid the loading massive data:
 * <p>OneToMany {@link OrderItem}</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "size")
    private int size;

    @Column(name = "color")
    private String color;

    @Column(name = "available")
    private boolean available;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    // lombok @RequiredArgsConstructor generates constructor for all final or @NonNull fields
//    @NonNull
    private /*final*/ Product product;

    /**
     * need many-to-many relations 'cause certain product details can have many images as well as
     * the same image can be related to many product details
     * <p>This side owns the relationship</p>
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // fetch = FetchType.LAZY by default
    @JoinTable(name = "product_detail_product_image", joinColumns = @JoinColumn(name = "product_detail_id"),
    inverseJoinColumns = @JoinColumn(name = "product_image_id"))
    private Set<ProductImage> productImages;

// NB: if ProductImage image object is non-persisted, I'll get
// java.lang.NullPointerException at com.vlad.store.model.ProductImage.hashCode(ProductImage.java:90)
// as image.id == null
// And if ProductDetail detail object is non-persisted, I'll get the same
    public void addProductImages(ProductImage ... images) {
        if (productImages == null) productImages = new HashSet<>();
        for (ProductImage image :
                images) {
            productImages.add(image);
            image.getProductDetails().add(this);
        }
    }

    public void removeProductImages(ProductImage ... images) {
        if (productImages != null && productImages.size() > 0) {
            for (ProductImage image :
                    images) {
                productImages.remove(image);
                image.getProductDetails().remove(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDetail)) return false;
        return id != null && id.equals(((ProductDetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31 * id.intValue();
    }
}
