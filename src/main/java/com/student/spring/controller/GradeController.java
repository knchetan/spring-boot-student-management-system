package com.student.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.spring.entity.Grade;
import com.student.spring.exception.StudentException;
import com.student.spring.service.GradeService;
import com.student.spring.service.StudentService;

/**
 * REST Controller for managing Grade entities.
 *
 * Exposes endpoints to add, retrieve, update, and delete grades.
 * The controller relies on the GradeService for business logic.
 */

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;
    private final StudentService studentService;
    
    @Autowired
    public GradeController(GradeService gradeService, StudentService studentService) {
        this.gradeService = gradeService;
        this.studentService = studentService;
    }
    
    /**
     * Creates a new grade (with standard) and assigns it to a student.
     * The studentId is passed as a request parameter.
     *
     * Example endpoint: POST /grades?studentId=1
     *
     * @param grade the Grade object in JSON (grade letter and standard)
     * @param studentId the ID of the student to assign this grade to
     * @return the created Grade object
     * @throws StudentException if the student does not exist or registration fails
     */
    @PostMapping
    public Grade addGradeAndStandard(@RequestBody Grade grade, @RequestParam int studentId) throws StudentException {
        if (!studentService.isStudentExists(studentId)) {
            throw new StudentException("Student not found with ID: " + studentId);
        }
        // This method associates the grade with the student; business logic is handled in the service layer.
        int gradeId = gradeService.addGradeForStudent(grade, studentId);
        grade.setGradeId(gradeId);
        return grade;
    }
    
    /**
     * Retrieves all grade records.
     *
     * Example endpoint: GET /grades
     *
     * @return a list of Grade objects
     * @throws StudentException if retrieval fails
     */
    @GetMapping
    public List<Grade> getAllGrades() throws StudentException {
        return gradeService.getAllGrades();
    }
    
    /**
     * Retrieves all grades associated with a specific student.
     *
     * Example endpoint: GET /grades/byStudent/1
     *
     * @param studentId the student's ID
     * @return a list of Grade objects associated with that student
     * @throws StudentException if retrieval fails
     */
    @GetMapping("/byStudent/{studentId}")
    public List<Grade> getGradesByStudentId(@PathVariable int studentId) throws StudentException {
        return gradeService.getGradesByStudentId(studentId);
    }
    
    /**
     * Updates an existing grade record.
     *
     * Example endpoint: PUT /grades/{gradeId}
     *
     * @param gradeId the ID of the grade to update
     * @param grade the Grade object in JSON with updated details
     * @return the updated Grade object
     * @throws StudentException if the update fails
     */
    @PutMapping("/{gradeId}")
    public Grade updateGrade(@PathVariable int gradeId, @RequestBody Grade grade) throws StudentException {
        grade.setGradeId(gradeId);
        gradeService.updateGrade(grade);
        return grade;
    }
    
    /**
     * Deletes a grade record by its ID.
     *
     * Example endpoint: DELETE /grades/{gradeId}
     *
     * @param gradeId the ID of the grade to delete
     * @return a success message if deletion is successful
     * @throws StudentException if deletion fails
     */
    @DeleteMapping("/{gradeId}")
    public String deleteGrade(@PathVariable int gradeId) throws StudentException {
        gradeService.deleteGrade(gradeId);
        return "Grade deleted successfully";
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
//  *      A grade record represents a combination of a grade letter (e.g., A+, A, B+, etc.),
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
//                                         addGradeAndStandard();
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
//         public void addGradeAndStandard() {
//                 try {
//                         logger.info("\nEnter your grade - A+, A, B+, B, C+, C, D+, D, E, F: ");
//                         String gradeInput = InputUtil.getString().trim().toUpperCase();
//                         logger.info("Enter the standard (e.g., 1,2,3,...): ");
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