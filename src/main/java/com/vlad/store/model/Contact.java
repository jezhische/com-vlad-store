package com.vlad.store.model;

import com.vlad.store.model.roles.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "acc_number")
    private int accNumber;

    @OneToMany(mappedBy = "contact",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @Column(name = "addresses")
    private List<Address> addresses;

}