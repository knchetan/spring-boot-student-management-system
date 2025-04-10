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
import org.springframework.web.bind.annotation.RestController;

import com.student.spring.entity.Activity;
import com.student.spring.exception.StudentException;
import com.student.spring.service.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    
    /**
     * Creates a new Activity.
     * 
     * Example endpoint: POST /activities
     * The Activity data is provided in the request body as JSON.
     *
     * @param activity the Activity object to create.
     * @return the created Activity (with the generated ID).
     * @throws StudentException if creation fails.
     */
    @PostMapping
    public Activity addActivity(@RequestBody Activity activity) throws StudentException {
        int id = activityService.addActivity(activity);
        activity.setActivityId(id);
        return activity;
    }
    
    /**
     * Retrieves all Activity records.
     * 
     * Example endpoint: GET /activities
     *
     * @return a list of Activity objects.
     * @throws StudentException if retrieval fails.
     */
    @GetMapping
    public List<Activity> getAllActivities() throws StudentException {
        return activityService.getAllActivities();
    }
    
    /**
     * Updates an existing Activity.
     * 
     * Example endpoint: PUT /activities/{id}
     * The updated Activity data is provided in the request body.
     *
     * @param id the ID of the Activity to update.
     * @param activity the Activity object with updated details.
     * @return the updated Activity object.
     * @throws StudentException if update fails.
     */
    @PutMapping("/{id}")
    public Activity updateActivity(@PathVariable int id, @RequestBody Activity activity) throws StudentException {
        activity.setActivityId(id);
        activityService.updateActivity(activity);
        return activity;
    }
    
    /**
     * Deletes an Activity record.
     * 
     * Example endpoint: DELETE /activities/{id}
     *
     * @param id the ID of the Activity to delete.
     * @return a confirmation message.
     * @throws StudentException if deletion fails.
     */
    @DeleteMapping("/{id}")
    public String deleteActivity(@PathVariable int id) throws StudentException {
        activityService.deleteActivity(id);
        return "Activity deleted successfully.";
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