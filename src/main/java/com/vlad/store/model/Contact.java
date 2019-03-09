package com.vlad.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/**
 * Customer details for {@link Customer}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    // "this way, the id column serves as both Primary Key and FK"
    @MapsId // simply it means that contact.id == customer.id
    private Customer customer;

    @Column(name = "name")
    String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

//    @Column(name = "billing_number")
//    private int billingNumber;

//    // customer can have many addresses to send purchases
//    @OneToMany(mappedBy = "contact",
//    cascade = CascadeType.ALL,
//    orphanRemoval = true)
//    @Column(name = "addresses")
//    private List<Address> addresses;

    // I can to refuse @OneToMany addresses collection 'cause it's enough to have @ManyToOne relation with Contact in Address class

}
