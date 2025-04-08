package com.student.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.spring.entity.Student;

/**
 * Repository interface for Student entities.
 *
 * Extends JpaRepository to provide CRUD operations and query methods for the Student entity.
 */

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
}
