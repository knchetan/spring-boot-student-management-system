package com.student.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.student.spring.dto.GradeDTO;
import com.student.spring.exception.StudentException;
import com.student.spring.service.GradeService;
import com.student.spring.service.StudentService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing Grade entities using DTOs.
 */
@RestController
@RequestMapping("/grades")
public class GradeController {

    private static final Logger logger = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    // POST: Add Grade
    @PostMapping
    public ResponseEntity<?> addGrade(@RequestBody @Valid GradeDTO gradeDTO, @RequestParam int studentId) {
        try {
            if (!studentService.isStudentExists(studentId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Student not found with ID: " + studentId);
            }
            int gradeId = gradeService.addGrade(gradeDTO);
            gradeDTO.setGradeId(gradeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(gradeDTO);
        } catch (StudentException se) {
            logger.error("Error adding grade", se);
            return ResponseEntity.badRequest().body("Error: " + se.getMessage());
        }
    }

    // GET: All Grades
    @GetMapping
    public ResponseEntity<?> getAllGrades() {
        try {
            List<GradeDTO> grades = gradeService.getAllGrades();
            return ResponseEntity.ok(grades);
        } catch (StudentException se) {
            logger.error("Error retrieving grades", se);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + se.getMessage());
        }
    }

    // GET: Grades by student
    @GetMapping("/byStudent/{studentId}")
    public ResponseEntity<?> getGradesByStudentId(@PathVariable int studentId) {
        try {
            List<GradeDTO> grades = gradeService.getGradesByStudentId(studentId);
            return ResponseEntity.ok(grades);
        } catch (StudentException se) {
            logger.error("Error retrieving grades for student {}", studentId, se);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + se.getMessage());
        }
    }

    // PUT: Update grade
    @PutMapping("/{gradeId}")
    public ResponseEntity<?> updateGrade(@PathVariable int gradeId, @RequestBody @Valid GradeDTO gradeDTO) {
        try {
            gradeDTO.setGradeId(gradeId);
            gradeService.updateGrade(gradeDTO);
            return ResponseEntity.ok(gradeDTO);
        } catch (StudentException se) {
            logger.error("Error updating grade", se);
            return ResponseEntity.badRequest().body("Error: " + se.getMessage());
        }
    }

    // DELETE: Delete grade
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<?> deleteGrade(@PathVariable int gradeId) {
        try {
            gradeService.deleteGrade(gradeId);
            return ResponseEntity.ok("Grade deleted successfully");
        } catch (StudentException se) {
            logger.error("Error deleting grade", se);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + se.getMessage());
        }
    }
}


































// package com.student.spring.controller;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.student.spring.entity.Grade;
// import com.student.spring.exception.StudentException;
// import com.student.spring.service.GradeService;
// import com.student.spring.service.StudentService;
// import com.student.spring.util.InputUtil;

// /**
//  *      The GradeController class manages the console-based menu for grade operations.
//  *
//  *      A grade record represents a combination of a grade letter (se.g., A+, A, B+, etc.),
//  *      and each student is associated with one such grade record.
//  *
//  *      The controller interacts with the GradeService to perform CRUD operations and with the StudentService to validate
//  *      the existence of student records. User input is handled through a combination of InputUtil and a Scanner instance.
//  */

// @RestController
// @RequestMapping("/grade")
// public class GradeController {
//         private static final Logger logger = LoggerFactory.getLogger(GradeController.class);
        
//         @Autowired
//         private GradeService gradeService;

//         @Autowired
//         private StudentService studentService;        
        
//         /**
//          * Starts the main loop for the console application.
//          */
//         public void start()
//         {
//                 logger.info("Starting GradeController menu");
//                 while (true) {

//                         logger.info("\t\t----- Grade Menu -----");
//                         logger.info("\n1. Add Grade and Standard");
//                         logger.info("2. Update Grade");
//                         logger.info("3. Delete Grade");
//                         logger.info("4. Exit Menu");
//                         logger.info("Enter your choice: ");
//                         int choice = InputUtil.getInteger();
//                         switch (choice) {
//                                 case 1:
//                                         addGrade();
//                                         break;
//                                 case 2:
//                                         updateGrade();
//                                         break;
//                                 case 3:
//                                         deleteGrade();
//                                         break;
//                                 case 4:
//                                         logger.info("Exiting Menu. Returning to Main Menu.");
//                                         return;
//                                 default:
//                                         logger.warn("Invalid choice, please try again.");
//                         }

//                 }
//         }

//         /**
//          * Collects grade details and assigns the grade to a student.
//          * <p>
//          * First, it prompts the user for grade and standard details, then asks for the student ID.
//          * If the student ID does not exist (as validated by StudentService), the grade assignment is aborted.
//          * Otherwise, it calls the service to add the grade for the specified student.
//          * </p>
//          */
//         public void addGrade() {
//                 try {
//                         logger.info("\nEnter your grade - A+, A, B+, B, C+, C, D+, D, E, F: ");
//                         String gradeInput = InputUtil.getString().trim().toUpperCase();
//                         logger.info("Enter the standard (se.g., 1,2,3,...): ");
//                         int standardInput = InputUtil.getInteger();
                        
//                         logger.info("Enter the student ID to assign the grade: ");
//                         int studentId = InputUtil.getInteger();
                        
//                         if (!studentService.isStudentExists(studentId)) {
//                                 logger.warn("Student with ID {} does not exist. Please register the student first." + studentId);
//                                 return;
//                         }

//                         Grade grade = new Grade(gradeInput, standardInput);
//                         int gradeId = gradeService.addGradeForStudent(grade, studentId);
//                         logger.info("Grade added successfully with ID: {} and assigned to student ID: {}" + gradeId + studentId);
//                 } catch (StudentException se) {
//                         logger.error("Error adding grade: {}" + se.getMessage());
//                 }
//         }

//         /**
//          * Updates an existing grade record.
//          * 
//          * @return void.
//          */
//         public void updateGrade() {
//                 try {
//                         logger.info("Enter grade ID to update: ");
//                         int gradeId = InputUtil.getInteger();
//                         logger.info("Enter new grade (A+, A, B+, B, C+, C, D+, D, E, F): ");
//                         String newGrade = InputUtil.getString().trim().toUpperCase();
                   
//                         logger.info("Enter new standard: ");
//                         int newStandard = InputUtil.getInteger();
                   
//                         Grade grade = new Grade(newGrade, newStandard);
//                         grade.setGradeId(gradeId);
//                         grade.setStandard(newStandard);
                   
//                         gradeService.updateGrade(grade);
//                         logger.info("Grade updated successfully.");
//                 } catch (StudentException se) {
//                         logger.error("Error updating grade: " + se.getMessage());
//                 }
//         }

//         /**
//          * Deletes a grade record.
//          * 
//          * @return void.
//          */
//         public void deleteGrade() {
//                 try {
//                         logger.info("Enter grade ID to delete: ");
//                         int gradeId = InputUtil.getInteger();
//                         gradeService.deleteGrade(gradeId);
//                         logger.info("Grade deleted successfully.");
//                 } catch (StudentException se) {
//                         logger.error("Error deleting grade: " + se.getMessage());
//                 }
//         }
// }