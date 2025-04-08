package com.student.spring.service;

import java.util.List;

import com.student.spring.entity.Student;
import com.student.spring.exception.StudentException;

/**
 * Defines business operations for Student entities.
 */
public interface StudentService {
    /**
     * Registers a new student by saving the Student entity.
     *
     * @param student the Student entity to be saved.
     * @return the generated student ID.
     * @throws StudentException if registration fails.
     */
    int registerStudent(Student student) throws StudentException;
    
    /**
     * Retrieves all student records.
     *
     * @return a list of Student entities.
     * @throws StudentException if retrieval fails.
     */
    List<Student> getAllStudents() throws StudentException;
    
    /**
     * Retrieves a student by their ID.
     *
     * @param studentId the student ID.
     * @return the Student entity, or null if not found.
     * @throws StudentException if retrieval fails.
     */
    Student getStudentById(int studentId) throws StudentException;
    
    /**
     * Checks if a student exists with the given ID.
     *
     * @param studentId the student ID.
     * @return true if the student exists; false otherwise.
     * @throws StudentException if an error occurs.
     */
    boolean isStudentExists(int studentId) throws StudentException;
    
    /**
     * Updates an existing student record.
     *
     * @param student the Student entity with updated details.
     * @throws StudentException if an error occurs during update.
     */
    void updateStudent(Student student) throws StudentException;
    
    /**
     * Deletes a student record based on the student ID.
     *
     * @param studentId the student ID to delete.
     * @throws StudentException if an error occurs during deletion.
     */
    void deleteStudent(int studentId) throws StudentException;
}
