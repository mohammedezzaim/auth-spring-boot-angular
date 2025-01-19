package org.example.back.security.dao;

import org.example.back.security.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ezzaim Mohammed
 **/
public interface RoleDao extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
