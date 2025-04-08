package com.student.spring.service;

import java.util.List;

import com.student.spring.entity.Grade;
import com.student.spring.exception.StudentException;

/**
 * GradeService handles business operations related to student grades,
 * including registration, fetching, update, and deletion.
 */
public interface GradeService {

    /**
     * Registers a new grade in the database and returns the generated grade ID.
     * 
     * @param grade the Grade object containing the grade details.
     * @return the generated grade ID.
     * @throws StudentException if registration fails.
     */
    int addGradeAndStandard(Grade grade) throws StudentException;

    /**
     * Adds a grade along with standard details for a student.
     *
     * @param grade the Grade object containing grade letter and standard.
     * @param studentId the ID of the student to assign this grade.
     * @return the generated grade ID.
     * @throws StudentException if an error occurs while saving the grade or updating the student.
     */
    int addGradeForStudent(Grade grade, int studentId) throws StudentException;    

    /**
     * Retrieves a list of grades associated with a student.
     * 
     * @param studentId the student ID.
     * @return a list of Grade objects.
     * @throws StudentException if retrieval fails.
     */
    List<Grade> getGradesByStudentId(int studentId) throws StudentException;
    
    /**
     * Retrieves all grade records.
     * 
     * @return a list of all Grade objects.
     * @throws StudentException if retrieval fails.
     */
    List<Grade> getAllGrades() throws StudentException;

    /**
     * Updates an existing grade record.
     * 
     * @param grade the Grade object with updated details.
     * @throws StudentException if an error occurs during update.
     */
    void updateGrade(Grade grade) throws StudentException;

    /**
     * Deletes a grade record based on the grade ID.
     * 
     * @param gradeId the grade ID to delete.
     * @throws StudentException if an error occurs during deletion.
     */
    void deleteGrade(int gradeId) throws StudentException;
}
