package org.example.back.security.dao;

import org.example.back.security.bean.UserDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ezzaim Mohammed
 **/
public interface UserDetailsDao extends JpaRepository<UserDetailsImpl, Long> {
    Optional<UserDetailsImpl> findByEmail(String email);
    boolean existsUserByEmail(String email);
    boolean existsByEmail(String email);
}
