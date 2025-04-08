package com.student.spring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.student.spring.entity.Membership;
import com.student.spring.exception.StudentException;
import com.student.spring.repository.MembershipRepository;
import com.student.spring.service.MembershipService;

@Service
public class MembershipServiceImpl implements MembershipService {

    private static final Logger logger = LoggerFactory.getLogger(MembershipServiceImpl.class);
    
    private final MembershipRepository membershipRepository;
    
    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }
    
    @Override
    public int addMembership(Membership membership) throws StudentException {
        try {
            Membership saved = membershipRepository.save(membership);
            return saved.getMembershipId();
        } catch (Exception se) {
            logger.error("Error in adding membership" + se);
            throw new StudentException("Error in adding membership: " + se.getMessage());
        }
    }
    
    @Override
    public Membership getMembershipByStudentId(int studentId) throws StudentException {
        try {
            Membership membership = membershipRepository.findByStudentStudentId(studentId);
            return membership;
        } catch (Exception se) {
            logger.error("Error retrieving membership for student ID: {}" + studentId + se);
            throw new StudentException("Error retrieving membership for student ID " + studentId + ": " + se.getMessage());
        }
    }
    
    @Override
    public List<Membership> getAllMemberships() throws StudentException {
        try {
            List<Membership> memberships = membershipRepository.findAll();
            return memberships;
        } catch (Exception se) {
            logger.error("Error retrieving all memberships" + se);
            throw new StudentException("Error retrieving all memberships: " + se.getMessage());
        }
    }
    
    @Override
    public void updateMembership(Membership membership) throws StudentException {
        try {
            membershipRepository.save(membership);
        } catch (Exception se) {
            logger.error("Error updating membership" + se);
            throw new StudentException("Error updating membership: " + se.getMessage());
        }
    }
    
    @Override
    public void deleteMembership(int membershipId) throws StudentException {
        try {
            membershipRepository.deleteById(membershipId);
        } catch (Exception se) {
            logger.error("Error deleting membership" + se);
            throw new StudentException("Error deleting membership: " + se.getMessage());
        }
    }
}
