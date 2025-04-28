package com.student.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.student.spring.dto.MembershipDTO;
import com.student.spring.entity.Membership;
import com.student.spring.exception.StudentException;
import com.student.spring.mapper.MembershipMapper;
import com.student.spring.repository.MembershipRepository;
import com.student.spring.service.MembershipService;

/**
 * Service implementation class for managing membership-related operations.
 *
 * This service provides the core business logic for creating, retrieving,
 * updating, and deleting membership records. It supports queries based on
 * membership ID or associated student ID.
 */
@Service
public class MembershipServiceImpl implements MembershipService {

    private static final Logger logger = LoggerFactory.getLogger(MembershipServiceImpl.class);

    @Autowired
    private MembershipRepository membershipRepository;

    /**
     * Adds a new membership.
     *
     * @param membershipDTO the membership data to be added
     * @return the ID of the newly created membership
     * @throws StudentException if the membership cannot be saved
     */
    @Override
    public int addMembership(MembershipDTO membershipDTO) throws StudentException {
        try {
            Membership membership = MembershipMapper.toEntity(membershipDTO);
            Membership newMembership = membershipRepository.save(membership);
            return newMembership.getMembershipId();
        } catch (Exception se) {
            logger.error("Error in adding membership: {}", se);
            throw new StudentException("Error in adding membership: " + se.getMessage());
        }
    }

    /**
     * Retrieves the membership associated with a specific student ID.
     *
     * @param studentId the ID of the student
     * @return the membership DTO associated with the student
     * @throws StudentException if the membership is not found
     */
    @Override
    public MembershipDTO getMembershipByStudentId(int studentId) throws StudentException {
        try {
            Membership membership = membershipRepository.findByStudentStudentId(studentId)
                .orElseThrow(() -> new StudentException("Membership not found for Student ID: " + studentId));
            return MembershipMapper.toDTO(membership);
        } catch (Exception se) {
            logger.error("Error retrieving membership for student ID: {}", studentId, se);
            throw new StudentException("Error retrieving membership for student ID " + studentId + ": " + se.getMessage());
        }
    }

    /**
     * Retrieves all memberships in the system.
     *
     * @return a list of MembershipDTOs
     * @throws StudentException if retrieval fails
     */
    @Override
    public List<MembershipDTO> getAllMemberships() throws StudentException {
        try {
            List<Membership> memberships = membershipRepository.findAll();
            return memberships.stream()
                .map(MembershipMapper::toDTO)
                .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error retrieving all memberships: {}", se);
            throw new StudentException("Error retrieving all memberships: " + se.getMessage());
        }
    }

    /**
     * Retrieves a membership by its ID.
     *
     * @param membershipId the ID of the membership
     * @return the corresponding MembershipDTO
     * @throws StudentException if the membership is not found
     */
    @Override
    public MembershipDTO getMembershipById(int membershipId) throws StudentException {
        try {
            Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new StudentException("Membership not found for ID: " + membershipId));
            return MembershipMapper.toDTO(membership);
        } catch (Exception se) {
            logger.error("Error retrieving membership for membership ID: {}", membershipId, se);
            throw new StudentException("Error retrieving membership for membership ID " + membershipId + ": " + se.getMessage());
        }
    }

    /**
     * Updates an existing membership.
     *
     * @param membershipDTO the updated membership data
     * @throws StudentException if the update operation fails
     */
    @Override
    public void updateMembership(MembershipDTO membershipDTO) throws StudentException {
        try {
            Membership membership = MembershipMapper.toEntity(membershipDTO);
            membershipRepository.save(membership);
        } catch (Exception se) {
            logger.error("Error updating membership: {}", se);
            throw new StudentException("Error updating membership: " + se.getMessage());
        }
    }

    /**
     * Deletes a membership by its ID.
     *
     * @param membershipId the ID of the membership to delete
     * @throws StudentException if the delete operation fails
     */
    @Override
    public void deleteMembership(int membershipId) throws StudentException {
        try {
            membershipRepository.deleteById(membershipId);
        } catch (Exception se) {
            logger.error("Error deleting membership: {}", se);
            throw new StudentException("Error deleting membership: " + se.getMessage());
        }
    }
}

