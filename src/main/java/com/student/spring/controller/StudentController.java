package com.student.spring.controller;

import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.student.spring.dto.StudentDTO;
import com.student.spring.dto.StudentInputDTO;
import com.student.spring.exception.StudentException;
import com.student.spring.service.StudentService;
import jakarta.validation.Valid;

/**
 * REST controller for managing Student entities.
 * Handles CRUD operations and integrates message localization using MessageSource.
 */
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private MessageSource messageSource;

    /**
     * GET /students - Retrieves all students.
     *
     * @return a list of StudentDTOs or an error message
     */
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        logger.info("GET /students - Fetching all students");
        try {
            List<StudentDTO> studentDTOs = studentService.getAllStudents();
            logger.info("Successfully fetched {} student(s)", studentDTOs.size());
            return ResponseEntity.ok(studentDTOs);
        } catch (StudentException se) {
            logger.error("Error fetching all students", se);
            String msg = messageSource.getMessage("error.internal", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    /**
     * GET /students/{studentId} - Retrieves a student by ID.
     *
     * @param studentId the ID of the student
     * @return the StudentDTO or an error message
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable("studentId") int studentId) {
        logger.info("GET /students/{} - Fetching student by ID", studentId);
        try {
            StudentDTO studentDTO = studentService.getStudentById(studentId);
            if (studentDTO == null) {
                logger.warn("Student not found with ID: {}", studentId);
                String msg = messageSource.getMessage("student.notfound", new Object[]{studentId}, Locale.getDefault());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
            logger.info("Student with ID {} found", studentId);
            return ResponseEntity.ok(studentDTO);
        } catch (StudentException se) {
            logger.error("Error retrieving student with ID: {}", studentId, se);
            String msg = messageSource.getMessage("error.internal", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }

    /**
     * POST /students - Registers a new student.
     *
     * @param studentInputDTO the student input data in flat JSON format
     * @return the created student DTO or error message
     */
    @PostMapping
    public ResponseEntity<?> registerStudent(@RequestBody @Valid StudentInputDTO studentInputDTO) {
        logger.info("POST /students - Registering new student");
        try {
            StudentDTO savedStudent = studentService.registerStudentFromInput(studentInputDTO);
            logger.info("Student registered successfully with ID: {}", savedStudent.getStudentId());
            String msg = messageSource.getMessage("student.created", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (StudentException se) {
            logger.error("Error registering student", se);
            String msg = messageSource.getMessage("error.internal", null, Locale.getDefault());
            return ResponseEntity.badRequest().body(msg);
        }
    }

    /**
     * PUT /students/{studentId} - Updates an existing student.
     *
     * @param studentId the ID of the student
     * @param studentInputDTO the updated student input data in flat JSON format
     * @return the updated student DTO or error message
     */
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable("studentId") int studentId,
                                           @RequestBody @Valid StudentInputDTO studentInputDTO) {
        logger.info("PUT /students/{} - Updating student", studentId);
        try {
            if (!studentService.isStudentExists(studentId)) {
                logger.warn("Update failed - Student not found with ID: {}", studentId);
                String msg = messageSource.getMessage("student.notfound", new Object[]{studentId}, Locale.getDefault());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }

            StudentDTO updatedStudent = studentService.updateStudentFromInput(studentId, studentInputDTO);
            logger.info("Student updated successfully with ID: {}", studentId);
            String msg = messageSource.getMessage("student.updated", null, Locale.getDefault());
            return ResponseEntity.ok(updatedStudent);
        } catch (StudentException se) {
            logger.error("Error updating student with ID: {}", studentId, se);
            String msg = messageSource.getMessage("error.internal", null, Locale.getDefault());
            return ResponseEntity.badRequest().body(msg);
        }
    }


    /**
     * DELETE /students/{studentId} - Deletes a student by ID.
     *
     * @param studentId the ID of the student
     * @return a success or error message
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") int studentId) {
        logger.info("DELETE /students/{} - Deleting student", studentId);
        try {
            if (!studentService.isStudentExists(studentId)) {
                logger.warn("Delete failed - Student not found with ID: {}", studentId);
                String msg = messageSource.getMessage("student.notfound", new Object[]{studentId}, Locale.getDefault());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
            }
            studentService.deleteStudent(studentId);
            logger.info("Student deleted successfully with ID: {}", studentId);
            String msg = messageSource.getMessage("student.deleted", null, Locale.getDefault());
            return ResponseEntity.ok(msg);
        } catch (StudentException se) {
            logger.error("Error deleting student with ID: {}", studentId, se);
            String msg = messageSource.getMessage("error.internal", null, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
        }
    }
}





































// package com.student.spring.controller;

// import java.sql.Date;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Scanner;
// import java.util.Set;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;

// import com.student.spring.entity.Activity;
// import com.student.spring.entity.Membership;
// import com.student.spring.entity.Student;
// import com.student.spring.exception.StudentException;
// import com.student.spring.service.ActivityService;
// import com.student.spring.service.MembershipService;
// import com.student.spring.service.StudentService;
// import com.student.spring.util.InputUtil;
// import com.student.spring.util.Validator;

// /**
//  * The StudentController class manages the student registration menu and related operations.
//  *
//  * During registration, the controller ensures that prerequisite Grade, Membership, and Activity details exist.
//  * It then displays available records for each category and repeatedly prompts the user until valid selections
//  * are made. The selected Grade (which encapsulates both grade and standard), Membership, and Activity are then
//  * linked to the new student record, along with the student's personal details.
//  * Additionally, the controller provides options to view, update, or delete student records.
//  * When a student record is displayed, all associated details—including Grade, Membership, and Activity—are shown.
//  *
//  * This class relies on the StudentService, GradeService, MembershipService, and ActivityService to perform business operations,
//  * and on InputUtil and Validator for handling user input and validations.
//  */

// @Controller 
// public class StudentController {
//         private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
        
//         @Autowired
//         private StudentService studentService;
        
//         @Autowired
//         private MembershipService membershipService;
        
//         @Autowired
//         private ActivityService activityService;
        
//         private final Scanner scanner = new Scanner(System.in);
        
//         /**
//          * Starts the student registration menu loop.
//          */
//         public void start() {
//                 while (true) {
//                         logger.info("\t\t----- Student Registration Menu -----");
//                         logger.info("\n1. Register Student");
//                         logger.info("2. View Student Records");
//                         logger.info("3. Update Student Records");
//                         logger.info("4. Delete Student Records");
//                         logger.info("5. Exit Registration");
//                         logger.info("Enter your choice: ");
//                         int choice = InputUtil.getInteger();
//                         switch (choice) {
//                                 case 1:
//                                         if (!registerStudent()) {
//                                                 return;
//                                         } break;
//                                 case 2:
//                                         viewStudentRecords();
//                                         break;
//                                 case 3:
//                                         updateStudent();
//                                         break;
//                                 case 4:
//                                         deleteStudent();
//                                         break;
//                                 case 5:
//                                         logger.info("Exiting registration. Returning to Main Menu.");
//                                         return;
//                                 default:
//                                         logger.warn("Invalid choice, please try again.");
//                         }
//                 }
//         }
        
//         /**
//          * Registers a new student.
//          * 
//          * The method first retrieves available Grade, Membership, and Activity details.
//          * If any of these lists are empty, registration is aborted with a message.
//          * Then, it repeatedly prompts the user to select a valid Grade record, a valid Membership record,
//          * and a valid Activity record before proceeding to collect the student's personal details. 
//          * The selected Grade, Membership, and Activity are then linked to the student.
//          * 
//          * @return true if registration is successful; false otherwise.
//          */
//         public boolean registerStudent() {
//             try {
//                     logger.info("Enter student details:");
//                     logger.info("\nEnter first name: ");
//                     String firstName = InputUtil.getString();
//                     logger.info("Enter last name: ");
//                     String lastName = InputUtil.getString();
//                     logger.info("Enter date of birth (yyyy-MM-dd): ");
//                     Date dob = InputUtil.getDate();
//                     logger.info("Enter email ID: ");
//                     String email = InputUtil.getEmail();
//                     logger.info("Enter phone number: ");
//                     String phone = InputUtil.getPhone();
//                     logger.info("Enter address: ");
//                     String address = InputUtil.getString();

//                     // Membership record
//                     List<Membership> memberships = membershipService.getAllMemberships();
//                     if (memberships.isEmpty()) {
//                             logger.warn("No membership details available. Please add membership details before registering a student.");
//                             return false;
//                     }
//                     Membership selectedMembership = null;
//                     while (selectedMembership == null) {
//                             logger.info("\nPlease enter the Membership ID from the below list to complete student registration ");
//                             logger.info("\t\t\nAvailable Memberships:");
//                             for (Membership membership : memberships) {
//                                     logger.info("Membership ID: " + membership.getMembershipId() + ",\tMembership Type: " + membership.getMembershipType());
//                             }
//                             logger.info("Enter the Membership ID: ");
//                             int membershipId = InputUtil.getInteger();
//                             for (Membership membership : memberships) {
//                                     if (membership.getMembershipId() == membershipId) {
//                                             selectedMembership = membership;
//                                             break;
//                                     }
//                             }
//                             if (selectedMembership == null) {
//                                     logger.warn("Invalid Membership ID. Please try again.");
//                             }
//                     }
                
//                     // Activity record
//                     List<Activity> activities = activityService.getAllActivities();
//                     if (activities.isEmpty()) {
//                         logger.warn("No activitiy details available. Please add activity details before registering a student.");
//                         return false;
//                     }                    
//                     Activity selectedActivity = null;
//                     while (selectedActivity == null) {
//                         logger.info("\nPlease enter the Activity ID from the below list to complete student registration ");
//                         logger.info("\t\t\nAvailable Activities:");
//                         for (Activity activity : activities) {
//                             logger.info("Activity ID: " + activity.getActivityId() + "\t\tActivity Name: " + activity.getActivityName());
//                         }
//                         logger.info("Enter the Activity ID: ");
//                         int activityId = InputUtil.getInteger();
                        
//                         for (Activity acivity : activities) {
//                             if (acivity.getActivityId() == activityId) {
//                                 selectedActivity = acivity;
//                                 break;
//                             }
//                         }
//                         if (selectedActivity == null) {
//                             logger.warn("Invalid Activity ID. Please enter a valid ID.");
//                         }
//                     }                    

//                     Student student = new Student(firstName, lastName, phone, email, address, dob);
                    
//                     student.setMembership(selectedMembership);
//                     Set<Activity> activitiesSet = new HashSet<>();
//                     activitiesSet.add(selectedActivity);
//                     student.setActivity(activitiesSet);                    

//                     int studentId = studentService.registerStudent(student);
//                     logger.info("Student registered successfully with ID: " + studentId);
//                     return true;
//             } catch (StudentException se) {
//                     logger.error("Error registering student: " + se.getMessage());
//                     return false;
//             }
//         }

        
//         /**
//          * Displays all student records in a formatted manner.
//          * The details include: ID, first name, last name, phone number, email, address, DOB, age, grade, membership, and activity.
//          */
//         public boolean viewStudentRecords() {
//                 try {
//                         List<Student> students = studentService.getAllStudents();
//                         if (students.isEmpty()) {
//                                 logger.info("Student records are empty. Please complete registration of students.");
//                         } else {
//                                 logger.info("\t\t---- Student Records ----\n");
//                                 for (Student student : students) {
//                                         logger.info(student.toString());
//                                         logger.info("---------------------------------------------------");
//                                 }
//                         }
//                         return true;
//                 } catch (StudentException se) {
//                         logger.error("Error viewing student records: " + se.getMessage());
//                         return false;
//                 }
//         }
        
//         /**
//          * Updates an existing student's details, including personal, grade, membership, and activity information.
//          * 
//          * @throws StudentException if an error occurs during the update process.
//          */
//         private void updateStudent() {
//             try {
//                 logger.info("Enter student ID to update: ");
//                 int studentId = InputUtil.getInteger();
//                 if (!studentService.isStudentExists(studentId)) {
//                     logger.info("No student found with ID: " + studentId);
//                     return;
//                 }
//                 Student student = studentService.getStudentById(studentId);
//                 logger.info("Current details: " + student);
                
//                 logger.info("Enter new first name (or press Enter to keep [" + student.getFirstName() + "]): ");
//                 String firstName = scanner.nextLine();
//                 if (!firstName.isEmpty()) {
//                     student.setFirstName(firstName);
//                 }
                
//                 logger.info("Enter new last name (or press Enter to keep [" + student.getLastName() + "]): ");
//                 String lastName = scanner.nextLine();
//                 if (!lastName.isEmpty()) {
//                     student.setLastName(lastName);
//                 }
                
//                 logger.info("Enter new address (or press Enter to keep [" + student.getAddress() + "]): ");
//                 String address = scanner.nextLine();
//                 if (!address.isEmpty()) {
//                     student.setAddress(address);
//                 }
                
//                 logger.info("Enter new phone number (or press Enter to keep [" + student.getPhoneNo() + "]): ");
//                 String phoneInput = scanner.nextLine();
//                 if (!phoneInput.isEmpty()) {
//                     if (Validator.isValidPhone(phoneInput)) {
//                         student.setPhoneNo(phoneInput);
//                     } else {
//                         logger.info("Invalid phone number. Keeping old phone number.");
//                     }
//                 }
                
//                 logger.info("Enter new date of birth (yyyy-MM-dd) (or press Enter to keep [" + student.getDob() + "]): ");
//                 String dobInput = scanner.nextLine();
//                 if (!dobInput.isEmpty()) {
//                     Date newDob = Validator.parseDate(dobInput);
//                     if (newDob != null) {
//                         student.setDob(newDob);
//                     } else {
//                         logger.warn("Invalid date format. Keeping old DOB.");
//                     }
//                 }         
                
//                 logger.info("\nDo you want to update Membership details? (Y/N): ");
//                 String updateMembershipChoice = InputUtil.getString().trim();
//                 if (updateMembershipChoice.equalsIgnoreCase("Y")) {
//                         List<Membership> memberships = membershipService.getAllMemberships();
//                         if (memberships.isEmpty()) {
//                                 logger.info("\nNo membership details available. Please add membership details first. Exiting...");
//                                 return;
//                         } else {
//                                 Membership selectedMembership = null;
//                                 while (selectedMembership == null) {
//                                         logger.info("\nAvailable Memberships:");
//                                         for (Membership membership : memberships) {
//                                                 logger.info("Membership ID: " + membership.getMembershipId() + "\tMembership Type: " + membership.getMembershipType());
//                                         }
//                                         logger.info("Enter the Membership ID from the above list: ");
//                                         int membershipId = InputUtil.getInteger();
//                                         for (Membership membership : memberships) {
//                                                 if (membership.getMembershipId() == membershipId) {
//                                                         selectedMembership = membership;
//                                                         break;
//                                                 }
//                                         }
//                                         if (selectedMembership == null) {
//                                                 logger.warn("Invalid Membership ID. Please try again.");
//                                         }
//                                 }
//                                 student.setMembership(selectedMembership);
//                         }
//                 }
                
//                 logger.info("\nDo you want to update Activity details? (Y/N): ");
//                 String updateActivityChoice = InputUtil.getString().trim();
//                 if (updateActivityChoice.equalsIgnoreCase("Y")) {
//                         List<Activity> activities = activityService.getAllActivities();
//                         if (activities.isEmpty()) {
//                                 logger.warn("\nNo activity details available. Please add activity details first. Exiting...");
//                                 return;
//                         } else {
//                                 Activity selectedActivity = null;
//                                 while (selectedActivity == null) {
//                                         logger.info("\nAvailable Activities:");
//                                         for (Activity activity : activities) {
//                                                 logger.info("Activity ID: " + activity.getActivityId() + "\t\tActivity Name: " + activity.getActivityName());
//                                         }
//                                         logger.info("Enter the Activity ID from the above list: ");
//                                         int activityId = InputUtil.getInteger();
//                                         for (Activity activity : activities) {
//                                                 if (activity.getActivityId() == activityId) {
//                                                         selectedActivity = activity;
//                                                         break;
//                                                 }
//                                         }
//                                         if (selectedActivity == null) {
//                                                 logger.warn("Invalid Activity ID. Please try again.");
//                                         }
//                                 }
//                                 Set<Activity> activitiesSet = new HashSet<>();
//                                 activitiesSet.add(selectedActivity);
//                                 student.setActivity(activitiesSet);
//                         }
//                 }                
                
//                 studentService.updateStudent(student);
//                 logger.info("Student updated successfully.");
//             } catch (StudentException se) {
//                 logger.error("Error updating student: " + se.getMessage());
//             }
//         }
        
//         /**
//          * Deletes a student record based on the student ID.
//          */
//         private void deleteStudent() {
//             try {
//                 logger.info("Enter student ID to delete: ");
//                 int studentId = InputUtil.getInteger();
//                 if (!studentService.isStudentExists(studentId)) {
//                     logger.info("No student found with ID: " + studentId);
//                     return;
//                 }
//                 studentService.deleteStudent(studentId);
//                 logger.info("Student deleted successfully.");
//             } catch (StudentException se) {
//                 logger.error("Error deleting student: " + se.getMessage());
//             }
//         }
// }
