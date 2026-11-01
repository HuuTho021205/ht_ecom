package com.ecommerce.ht_ecom.repository;

import com.ecommerce.ht_ecom.model.AppRole;
import com.ecommerce.ht_ecom.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
