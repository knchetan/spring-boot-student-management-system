package com.student.spring.service;

import java.util.List;
import com.student.spring.dto.MembershipDTO;
import com.student.spring.exception.StudentException;

/**
 * MembershipService handles business operations related to student membership,
 * including registration, fetching, update, and deletion.
 */

public interface MembershipService {

        /**
         * Registers a new membership in the database and returns the generated membership ID.
         * 
         * @param grade the Membership object containing the membership details.
         * @return the generated membership ID.
         * @throws StudentException if registration fails.
         */

        int addMembership(MembershipDTO membershipDTO) throws StudentException;

        /**
         * Retrieves the Membership associated with a student.
         * 
         * @param studentId the student ID.
         * @return Membership object.
         * @throws StudentException if retrieval fails.
         */

        MembershipDTO getMembershipByStudentId(int studentId) throws StudentException;

        /**
         *  Retrives the Membership associated with it's ID.
         * 
         *  @param id the membership ID.
         *  @return Membership object.
         *  @throws StudentException if retrieval fails.
         */

        MembershipDTO getMembershipById(int id) throws StudentException;

        /**
         * Retrieves all membership records.
         * 
         * @return a list of all Membership objects.
         * @throws StudentException if retrieval fails.
         */

        List<MembershipDTO> getAllMemberships() throws StudentException;        

        /**
         * Updates an existing membership record.
         * 
         * @param membership the Membership object with updated details.
         * @throws StudentException if an error occurs during update.
         */                

        void updateMembership(MembershipDTO membershipDTO) throws StudentException;

        /**
         * Deletes a membership record based on the membership ID.
         * 
         * @param membershipId the membership ID to delete.
         * @throws StudentException if an error occurs during deletion.
         */

        void deleteMembership(int membershipId) throws StudentException;
}
