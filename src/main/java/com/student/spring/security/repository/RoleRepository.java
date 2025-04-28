package com.student.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.student.spring.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
