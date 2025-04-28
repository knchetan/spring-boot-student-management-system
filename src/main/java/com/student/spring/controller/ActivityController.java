package com.student.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.student.spring.dto.ActivityDTO;
import com.student.spring.exception.StudentException;
import com.student.spring.service.ActivityService;

import jakarta.validation.Valid;

/**
 * REST Controller for managing activity-related operations.
 * 
 * This controller provides endpoints for creating, retrieving, updating, 
 * and deleting activities in the system. All endpoints are protected and 
 * require the caller to have the 'ADMIN' role.
 * 
 * Access Control: Only users with ROLE_ADMIN can access these endpoints.</p>
 */
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/activities")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;

    /**
     * POST /activities - Creates a new activity.
     *
     * @param activityDTO the activity data to be added
     * @return the created ActivityDTO and HTTP 201 status if successful,
     *         otherwise an error message with HTTP 400 status
     */
    @PostMapping
    public ResponseEntity<?> addActivity(@RequestBody @Valid ActivityDTO activityDTO) {
        try {
            int activityId = activityService.addActivity(activityDTO);
            activityDTO.setActivityId(activityId);
            return new ResponseEntity<>(activityDTO, HttpStatus.CREATED);
        } catch (StudentException se) {
            logger.error("Error adding activity", se);
            return new ResponseEntity<>("Error: " + se.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /activities - Retrieves all activities.
     *
     * @return a list of ActivityDTOs and HTTP 200 status if successful,
     *         otherwise an error message with HTTP 500 status
     */
    @GetMapping
    public ResponseEntity<?> getAllActivities() {
        try {
            List<ActivityDTO> list = activityService.getAllActivities();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (StudentException se) {
            logger.error("Error retrieving activities", se);
            return new ResponseEntity<>("Error: " + se.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * PUT /activities/{activityId} - Updates an existing activity.
     *
     * @param activityId the ID of the activity to update
     * @param activityDTO the updated activity data
     * @return the updated ActivityDTO and HTTP 200 status if successful,
     *         otherwise an error message with HTTP 400 status
     */
    @PutMapping("/{activityId}")
    public ResponseEntity<?> updateActivity(@PathVariable int activityId, @RequestBody @Valid ActivityDTO activityDTO) {
        try {
            activityDTO.setActivityId(activityId);
            activityService.updateActivity(activityDTO);
            return new ResponseEntity<>(activityDTO, HttpStatus.OK);
        } catch (StudentException se) {
            logger.error("Error in updating activity", se);
            return new ResponseEntity<>("Error: " + se.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE /activities/{activityId} - Deletes an activity by ID.
     *
     * @param activityId the ID of the activity to delete
     * @return a success message and HTTP 200 status if deleted,
     *         otherwise an error message with HTTP 404 status
     */
    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteActivity(@PathVariable int activityId) {
        try {
            activityService.deleteActivity(activityId);
            return new ResponseEntity<>("Activity deleted successfully.", HttpStatus.OK);
        } catch (StudentException se) {
            logger.error("Error in deleting activity", se);
            return new ResponseEntity<>("Error: " + se.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}



































// package com.student.spring.controller;

// import java.util.Scanner;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;

// import com.student.spring.entity.Activity;
// import com.student.spring.exception.StudentException;
// import com.student.spring.service.ActivityService;
// import com.student.spring.util.InputUtil;

// /**
//  * The ActivityController class manages the text-based menu for student activity operations.
//  *
//  *      This controller provides methods to add, update, and delete activity records.
//  *      An activity represents an event or participation that can be linked to multiple students (and vice versa) 
//  *      through a many-to-many relationship.
//  *      This class depends on the ActivityService to perform business operations and on InputUtil for handling
//  *      user input from the console.
//  *
//  * This class depends on the ActivityService to perform business operations and on InputUtil for handling
//  * user input from the console.
//  */

// @Controller
// public class ActivityController {
//         private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
        
//         @Autowired
//         private ActivityService activityService;
//         private Scanner scanner = new Scanner(System.in);

//         /**
//          * Starts the main loop for the console application.
//          */
//         public void start()
//         {
//                 logger.info("Starting ActivityController menu");
//                 while (true) {

//                         logger.info("\t\t----- Activities Menu -----");
//                         logger.info("1. Add Activity");
//                         logger.info("2. Update Activity");
//                         logger.info("3. Delete Activity");
//                         logger.info("4. Exit Menu");
//                         logger.info("Enter your choice: ");
//                         int choice = InputUtil.getInteger();
//                         switch (choice) {
//                                 case 1:
//                                         addActivity();
//                                         break;
//                                 case 2:
//                                         updateActivity();
//                                         break;
//                                 case 3:
//                                         deleteActivity();
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
//          *  This method collects activity name, and based on the user choice the activity type is assigned as Indoor or Outdoor.
//          *  
//          *	@return void
//         */

//         public void addActivity() {
//                 try {
//                         logger.info("Enter activity name to be added: ");
//                         String activityName = InputUtil.getString().trim();
//                         logger.info("Choose activity type for the above entered activity:1. Indoor Activity2. Outdoor Activity");
//                         int choice = scanner.nextInt();
//                         String activityType = "";
//                         switch(choice) {
//                                 case 1:
//                                         activityType = "Indoor Activity";
//                                         break;
//                                 case 2:
//                                         activityType = "Outdoor Activity";
//                                         break;
//                                 default:
//                                         logger.warn("Invalid choice. Please try again!");
//                         }
//                         Activity activity = new Activity(activityName, activityType);
//                         int activityId = activityService.addActivity(activity);
//                         logger.info("Activity added successfully with ID: " + activityId);
//                 } catch (StudentException se) {
//                         logger.error("Error adding activity: " + se.getMessage());
//                 }
//         }

//         /**      
//          *      Updates an existing activity record associated with specific Activity ID.
//          * 
//          *      @return void.
//          *      @throws StudentException if any error occurs during the update process.
//          */

//         public void updateActivity() {
//                 try {
//                         logger.info("Enter activity ID to update: ");
//                         int activityId = InputUtil.getInteger();
//                         logger.info("Enter new activity name: ");
//                         String newActivityName = InputUtil.getString().trim();
//                         logger.info("Choose new activity type:1. Indoor Activities2. Outdoor Activities");
//                         String choice = InputUtil.getString().trim();
//                         String newActivityType = "";
//                         switch(choice) {
//                                 case "1":
//                                         newActivityType = "Indoor Activities";
//                                         break;
//                                 case "2":
//                                         newActivityType = "Outdoor Activities";
//                                         break;
//                                 default:
//                                         logger.warn("Invalid choice, keeping old activity type.");
//                                         newActivityType = "";
//                         }
//                         Activity activity = new Activity(newActivityName, newActivityType);
//                         activity.setActivityId(activityId);
//                         activityService.updateActivity(activity);
//                         logger.info("Activity updated successfully.");
//                 } catch (StudentException se) {
//                         logger.error("Error updating activity: " + se.getMessage());
//                 }
//         }


//         /**
//          *      Attempts to delete an activity record for a specific Activity ID.
//          *      
//          *      @return void.
//          *      @throws StudentException if any error occurs during the deletion process.
//          */        

//         public void deleteActivity() {
//                 try {
//                         logger.info("Enter activity ID to delete: ");
//                         int activityId = InputUtil.getInteger();
//                         activityService.deleteActivity(activityId);
//                         logger.info("Activity deleted successfully.");
//                 } catch (StudentException se) {
//                         logger.error("Error deleting activity: " + se.getMessage());
//                 }
//         }
// }