package com.student.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.spring.entity.Grade;

/**
 * Repository interface for Grade entities.
 *
 * Extends JpaRepository to provide CRUD operations and query methods for the Grade entity.
 */

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    List<Grade> findByStudentsStudentId(int studentId);
}
