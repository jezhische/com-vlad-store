package com.vlad.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * NB: the following relations exists but didn't be reflected in this entity to avoid the loading massive data:
 * <p>OneToMany {@link Product}</p>
 * <p>OneToMany {@link ProducerImage}</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producers")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

//    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL, orphanRemoval = true) // default fetch = FetchType.LAZY
//    private List<Product> products;
}
