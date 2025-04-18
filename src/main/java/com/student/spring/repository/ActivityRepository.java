package com.student.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.spring.entity.Activity;

/**
 * Repository interface for Activity entities.
 *
 * Extends JpaRepository to provide CRUD operations and query methods for the Activity entity.
 */

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByStudentsStudentId(int studentId);

    Optional<Activity> findByActivityNameAndActivityType(String activityName, String activityType);

}

