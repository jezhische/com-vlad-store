package com.vlad.store.repository;

import com.vlad.store.model.Customer;
import com.vlad.store.model.Role;
import com.vlad.store.model.roles.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
    Role findById(long id);
}
