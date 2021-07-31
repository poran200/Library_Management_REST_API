package com.library.api.repository;/*
 * @created 7/28/2021
 *
 * @Author Poran chowdury
 */

import com.library.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndRole(String username, String role);
    Page<User> findAllByRole(String role, Pageable pageable);
   Optional<User> findByEmail(String email);
}
