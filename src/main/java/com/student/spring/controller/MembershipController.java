package com.student.spring.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.spring.entity.Membership;
import com.student.spring.exception.StudentException;
import com.student.spring.service.MembershipService;

/**
 * REST Controller for managing Membership entities.
 *
 * Exposes endpoints to add, retrieve, update, and delete memberships.
 * The controller relies on the MembershipService for business logic.
 */

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
    
    @Autowired
    private MembershipService membershipService;
    
    /**
     * Creates a new membership.
     *
     * The membership start date is automatically set to the current date.
     * The expiry date is determined based on the membership type:
     *   - Standard: current date + 3 months
     *   - Premium: current date + 6 months
     *   - Platinum: current date + 12 months
     * 
     * Example endpoint: POST /memberships
     *
     * @param membership the Membership entity containing the membershipType
     * @return the created Membership object (with computed start and expiry dates)
     * @throws StudentException if creation fails.
     */
    @PostMapping
    public Membership addMembership(@RequestBody Membership membership) throws StudentException {
        try {
            LocalDate currentDate = LocalDate.now();
            Date startDate = Date.valueOf(currentDate);
            int expiryMonths;
            String membershipType = membership.getMembershipType();
            
            if ("Standard".equalsIgnoreCase(membershipType)) {
                expiryMonths = 3;
            } else if ("Premium".equalsIgnoreCase(membershipType)) {
                expiryMonths = 6;
            } else if ("Platinum".equalsIgnoreCase(membershipType)) {
                expiryMonths = 12;
            } else {
                throw new StudentException("Invalid membership type: " + membershipType);
            }
            
            LocalDate expiryLocalDate = currentDate.plusMonths(expiryMonths);
            Date expiryDate = Date.valueOf(expiryLocalDate);
            
            membership.setStartDate(startDate);
            membership.setExpiryDate(expiryDate);
            
            int membershipId = membershipService.addMembership(membership);
            membership.setMembershipId(membershipId);
            logger.info("Membership added successfully with ID: {}", membershipId);
            return membership;
        } catch (StudentException se) {
            logger.error("Error adding membership: {}", se.getMessage(), se);
            throw se;
        }
    }
    
    /**
     * Retrieves all membership records.
     * 
     * Example endpoint: GET /memberships
     *
     * @return a list of Membership entities
     * @throws StudentException if retrieval fails.
     */
    @GetMapping
    public List<Membership> getAllMemberships() throws StudentException {
        return membershipService.getAllMemberships();
    }
    
    /**
     * Retrieves a membership by its ID.
     * 
     * Example endpoint: GET /memberships/{membershipId}
     *
     * @param membershipId the membership ID
     * @return the corresponding Membership entity
     * @throws StudentException if retrieval fails.
     */
    @GetMapping("/{membershipId}")
    public Membership getMembershipById(@PathVariable("membershipId") int membershipId) throws StudentException {
        Membership membership = membershipService.getMembershipByMembershipId(membershipId);
        if (membership == null) {
            throw new StudentException("Membership not found with ID: " + membershipId);
        }
        return membership;
    }
    
    /**
     * Updates an existing membership.
     *
     * Only the membershipType can be updated; start and expiry dates remain unchanged.
     * 
     * Example endpoint: PUT /memberships/{membershipId}
     *
     * @param membershipId the membership ID
     * @param membership the Membership entity with updated details (membershipType)
     * @return the updated Membership entity
     * @throws StudentException if update fails.
     */
    @PutMapping("/{membershipId}")
    public Membership updateMembership(@PathVariable("membershipId") int membershipId, @RequestBody Membership membership) throws StudentException {
        Membership existing = membershipService.getMembershipByMembershipId(membershipId);
        if (existing == null) {
            throw new StudentException("Membership not found with ID: " + membershipId);
        }
        existing.setMembershipType(membership.getMembershipType());
        membershipService.updateMembership(existing);
        logger.info("Membership updated successfully for ID: {}", membershipId);
        return existing;
    }
    
    /**
     * Deletes a membership by its ID.
     * 
     * Example endpoint: DELETE /memberships/{membershipId}
     *
     * @param membershipId the membership ID
     * @return a confirmation message.
     * @throws StudentException if deletion fails.
     */
    @DeleteMapping("/{membershipId}")
    public String deleteMembership(@PathVariable("membershipId") int membershipId) throws StudentException {
        membershipService.deleteMembership(membershipId);
        logger.info("Membership deleted successfully for ID: {}", membershipId);
        return "Membership deleted successfully";
    }
}

































// package com.student.spring.controller;

// import java.sql.Date;
// import java.time.LocalDate;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;

// import com.student.spring.entity.Membership;
// import com.student.spring.exception.StudentException;
// import com.student.spring.service.MembershipService;
// import com.student.spring.util.InputUtil;

// /**
//  * This class handles the membership menu.
//  * 
//  * When the user selects the exit option, control returns to the main menu.
//  * In this implementation, the membership start date is automatically set to the current date,
//  * and the expiry date is determined based on the membership type chosen:
//  *
//  *   Standard: Expires in 3 months
//  *   Premium: Expires in 6 months
//  *   Platinum: Expires in 12 months
//  *
//  * These dates are stored in the database and displayed when viewing student records.
//  */

// @Controller 
// public class MembershipController {
//         private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
        
//         @Autowired
//         private MembershipService membershipService;

//         /**
//          * Starts the membership menu loop.
//          */
//         public void start() {
//                 logger.info("Starting MembershipController menu");
//                 while (true) {
//                         logger.info("\t\t----- Student Membership Menu -----");
//                         logger.info("\n1. Add Membership for Student");
//                         logger.info("2. Update Membership");
//                         logger.info("3. Delete Membership");
//                         logger.info("4. Exit Menu");
//                         logger.info("Enter your choice: ");
//                         int choice = InputUtil.getInteger();
//                         switch (choice) {
//                                 case 1:
//                                 addMembership();
//                                 break;
//                                 case 2:
//                                 updateMembership();
//                                 break;
//                                 case 3:
//                                 deleteMembership();
//                                 break;
//                                 case 4:
//                                 logger.info("Exiting Menu. Returning to Main Menu.");
//                                 return;
//                                 default:
//                                 logger.warn("Invalid choice, please try again.");
//                         }
//                 }
//         }
        
//         /**
//          * Adds a new membership.
//          * 
//          * When adding a membership, the start date is automatically set to the current date.
//          * The expiry date is computed based on the membership type:
//          * 
//          *   Standard: current date + 3 months
//          *   Premium: current date + 6 months
//          *   Platinum: current date + 12 months
//          */
//         public void addMembership() {
//                 try {
//                         LocalDate currentDate = LocalDate.now();
//                         Date startDate = Date.valueOf(currentDate);
//                         int expiryMonths;
                        
//                         logger.info("\nChoose your membership type:1. Standard2. Premium3. Platinum");
//                         int choice = InputUtil.getInteger();
//                         String membershipType;
                        
//                         switch (choice) {
//                                 case 1:
//                                         membershipType = "Standard";
//                                         expiryMonths = 3;
//                                         break;
//                                 case 2:
//                                         membershipType = "Premium";
//                                         expiryMonths = 6;
//                                         break;
//                                 case 3:
//                                         membershipType = "Platinum";
//                                         expiryMonths = 12;
//                                         break;
//                                 default:
//                                         logger.warn("Invalid choice. Please try again!");
//                                         return;
//                         }
                        
//                         LocalDate expiryLocalDate = currentDate.plusMonths(expiryMonths);
//                         Date expiryDate = Date.valueOf(expiryLocalDate);
                        
//                         Membership membership = new Membership(startDate, expiryDate, membershipType);
//                         int membershipId = membershipService.addMembership(membership);
//                         logger.info("Membership added successfully with ID: " + membershipId);
//                 } catch (StudentException se) {
//                         logger.error("Error adding membership: " + se.getMessage());
//                 }
//         }
        
//         /**
//          * Updates an existing membership record.
//          * 
//          * This method currently allows the user to update membership details other than the start and expiry dates.
//          * The start and expiry dates are set automatically during membership creation.
//          * 
//          */
//         public void updateMembership() {
//                 try {
//                         logger.info("\nEnter membership ID to update: ");
//                         int membershipId = InputUtil.getInteger();
//                         Membership membership = membershipService.getMembershipByStudentId(membershipId);
//                         if (membership == null) {
//                                 logger.info("Membership not found.");
//                                 return;
//                         }
//                         logger.info("\nCurrent Membership Details: " + membership);
//                         logger.warn("Note: Start and Expiry dates cannot be modified.");
                        
//                         membershipService.updateMembership(membership);
//                         logger.info("Membership updated successfully.");
//                 } catch (StudentException se) {
//                         logger.error("Error updating membership: " + se.getMessage());
//                 }
//         }
        
//         /**
//          * Deletes a membership record.
//          */
//         public void deleteMembership() {
//                 try {
//                         logger.info("Enter membership ID to delete: ");
//                         int membershipId = InputUtil.getInteger();
//                         membershipService.deleteMembership(membershipId);
//                         logger.info("Membership deleted successfully.");
//                 } catch (StudentException se) {
//                         logger.error("Error deleting membership: " + se.getMessage());
//                 }
//         }
// }
