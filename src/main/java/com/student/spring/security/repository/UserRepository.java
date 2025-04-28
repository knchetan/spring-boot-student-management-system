package com.student.spring.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.student.spring.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}