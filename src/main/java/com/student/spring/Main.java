package com.student.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start the Student Management application.
 * 
 * This class is now a standard Spring Boot application entry point.
 * With the embedded web server in Spring Boot, your REST controllers (annotated with @RestController)
 * will handle HTTP requests.
 * 
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

































// package com.student.spring;

// import java.util.Scanner;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.student.spring.controller.ActivityController;
// import com.student.spring.controller.GradeController;
// import com.student.spring.controller.MembershipController;
// import com.student.spring.controller.StudentController;

// import jakarta.annotation.PostConstruct;

// /**
//  * Main class to start the Student Management application.
//  *
//  * This class is managed by Spring Boot and uses dependency injection to obtain
//  * its controller beans. It runs a console-based main menu loop (without REST endpoints).
//  */
// @SpringBootApplication
// public class Main {

//     private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
//     @Autowired
//     private StudentController studentController;
    
//     @Autowired
//     private GradeController gradeController;
    
//     @Autowired
//     private ActivityController activityController;
    
//     @Autowired
//     private MembershipController membershipController;
    
//     private Scanner scanner = new Scanner(System.in);
    
//     public static void main(String[] args) {
//         SpringApplication.run(Main.class, args);
//     }
    
//     /**
//      * Runs the main menu loop after all beans are initialized.
//      * This method is invoked automatically via @PostConstruct.
//      */
//     @PostConstruct
//     public void runApplication() {
//         while (true) {
//             logger.info("\t----- WELCOME TO STUDENT MANAGEMENT PORTAL -----");
//             logger.info("\t1. Proceed to Student Registration Menu");
//             logger.info("\t2. Proceed to Grade Menu");
//             logger.info("\t3. Proceed to Activity Menu");
//             logger.info("\t4. Proceed to Membership Menu");
//             logger.info("\t5. Exit Portal");
//             logger.info("Enter your choice: ");
//             int mainMenuChoice = scanner.nextInt();
//             scanner.nextLine();
//             switch (mainMenuChoice) {
//                 case 1:
//                     studentController.start();
//                     break;
//                 case 2:
//                     gradeController.start();
//                     break;
//                 case 3:
//                     activityController.start();
//                     break;
//                 case 4:
//                     membershipController.start();
//                     break;
//                 case 5:
//                     logger.info("Exiting Portal. Thank you.");
//                     scanner.close();
//                     System.exit(0);
//                     break;
//                 default:
//                     logger.warn("Invalid choice, please try again.");
//             }
//         }
//     }
// }
