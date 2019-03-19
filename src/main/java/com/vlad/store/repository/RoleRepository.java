package com.vlad.store.repository;

import com.vlad.store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
    Role findById(long id);

//    void delete(Role entity);
}
