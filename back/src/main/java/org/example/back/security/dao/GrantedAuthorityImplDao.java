package org.example.back.security.dao;

import org.example.back.security.bean.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ezzaim Mohammed
 **/
public interface GrantedAuthorityImplDao extends JpaRepository<GrantedAuthorityImpl, Integer> {
    Optional<GrantedAuthorityImpl> findByRole(String role);
}
