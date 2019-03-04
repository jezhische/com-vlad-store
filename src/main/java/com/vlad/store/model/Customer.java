package com.vlad.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers",
uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    @NotEmpty(message = "*Please provide your login")
    private String login;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column
    private boolean enabled;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY) // LAZY by default
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // contact id will be the same, as customer id, so it doesn't need to add "contact" column

    public void setRoles(Role... roles) {
        this.roles  = new HashSet<>();
        this.roles.addAll(Arrays.asList(roles));
    }

    public void addRoles(Role... roles) {
        if (this.roles == null) this.roles = new HashSet<>();
        for (Role r : roles) {
            if (!this.roles.contains(r)) this.roles.add(r);
        }
//        this.roles.addAll(Arrays.asList(roles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return this.id == ((Customer) o).id;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

}
