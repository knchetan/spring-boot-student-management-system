package com.student.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.spring.entity.Membership;

/**
 * Repository interface for Membership entities.
 *
 * Extends JpaRepository to provide CRUD operations and query methods for the Membership entity.
 */

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

    Membership findByStudentStudentId(int studentId);
}
