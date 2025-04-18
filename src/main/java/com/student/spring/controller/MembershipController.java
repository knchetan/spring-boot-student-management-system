package com.student.spring.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.student.spring.dto.MembershipDTO;
import com.student.spring.exception.StudentException;
import com.student.spring.service.MembershipService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);

    @Autowired
    private MembershipService membershipService;

    // POST: Add new membership
    @PostMapping
    public ResponseEntity<?> addMembership(@RequestBody @Valid  MembershipDTO membershipDTO) {
        try {
            LocalDate currentDate = LocalDate.now();
            membershipDTO.setStartDate(Date.valueOf(currentDate));

            int expiryMonths = switch (membershipDTO.getMembershipType().toLowerCase()) {
                case "standard" -> 3;
                case "premium" -> 6;
                case "platinum" -> 12;
                default -> throw new StudentException("Invalid membership type: " + membershipDTO.getMembershipType());
            };

            membershipDTO.setExpiryDate(Date.valueOf(currentDate.plusMonths(expiryMonths)));

            int membershipId = membershipService.addMembership(membershipDTO);
            membershipDTO.setMembershipId(membershipId);

            logger.info("Membership added successfully with ID: {}", membershipId);
            return ResponseEntity.status(HttpStatus.CREATED).body(membershipDTO);
        } catch (StudentException se) {
            logger.error("Error adding membership: {}", se.getMessage(), se);
            return ResponseEntity.badRequest().body("Error: " + se.getMessage());
        } catch (Exception se) {
            logger.error("Unexpected error while adding membership", se);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + se.getMessage());
        }
    }

    // GET: All memberships
    @GetMapping
    public ResponseEntity<?> getAllMemberships() {
        try {
            List<MembershipDTO> memberships = membershipService.getAllMemberships();
            return ResponseEntity.ok(memberships);
        } catch (StudentException se) {
            logger.error("Error retrieving memberships", se);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + se.getMessage());
        }
    }

    // GET: Membership by ID
    @GetMapping("/{membershipId}")
    public ResponseEntity<?> getMembershipById(@PathVariable("membershipId") int membershipId) {
        try {
            MembershipDTO membershipDTO = membershipService.getMembershipById(membershipId);
            if (membershipDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Membership not found with ID: " + membershipId);
            }
            return ResponseEntity.ok(membershipDTO);
        } catch (StudentException se) {
            logger.error("Error retrieving membership {}", membershipId, se);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + se.getMessage());
        }
    }

    // PUT: Update membership
    @PutMapping("/{membershipId}")
    public ResponseEntity<?> updateMembership(@PathVariable("membershipId") int membershipId,
                                              @RequestBody @Valid MembershipDTO membershipDTO) {
        try {
            MembershipDTO existing = membershipService.getMembershipById(membershipId);
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Membership not found with ID: " + membershipId);
            }

            membershipDTO.setMembershipId(membershipId);
            membershipDTO.setStartDate(existing.getStartDate());
            membershipDTO.setExpiryDate(existing.getExpiryDate());

            membershipService.updateMembership(membershipDTO);
            logger.info("Membership updated successfully for ID: {}", membershipId);
            return ResponseEntity.ok(membershipDTO);
        } catch (StudentException se) {
            logger.error("Error updating membership {}", membershipId, se);
            return ResponseEntity.badRequest().body("Error: " + se.getMessage());
        }
    }

    // DELETE: Delete membership
    @DeleteMapping("/{membershipId}")
    public ResponseEntity<?> deleteMembership(@PathVariable("membershipId") int membershipId) {
        try {
            membershipService.deleteMembership(membershipId);
            logger.info("Membership deleted successfully for ID: {}", membershipId);
            return ResponseEntity.ok("Membership deleted successfully");
        } catch (StudentException se) {
            logger.error("Error deleting membership {}", membershipId, se);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + se.getMessage());
        }
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
