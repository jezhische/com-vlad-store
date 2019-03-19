package com.vlad.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * NB: the following relations exists but didn't be reflected in this entity to avoid the loading massive data:
 * <p>OneToMany {@link ProductDetail}</p>
 * <p>OneToMany {@link OrderItem}</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "spec", columnDefinition = "text")
    private String specification;

    @Column(name = "available")
    private boolean available;

    /**
     * if it needs that the price would depend from product details (like size and color), this field has to be moved
     * to {@link ProductDetail}
     */
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private Producer producer;
}