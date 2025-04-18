package com.student.spring.service.impl;

import com.student.spring.dto.MembershipDTO;
import com.student.spring.entity.Membership;
import com.student.spring.exception.StudentException;
import com.student.spring.mapper.MembershipMapper;
import com.student.spring.repository.MembershipRepository;
import com.student.spring.service.MembershipService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipServiceImpl implements MembershipService {

    private static final Logger logger = LoggerFactory.getLogger(MembershipServiceImpl.class);

    @Autowired
    private MembershipRepository membershipRepository;

    @Override
    public int addMembership(MembershipDTO membershipDTO) throws StudentException {
        try {
            Membership membership = MembershipMapper.toEntity(membershipDTO);
            Membership saved = membershipRepository.save(membership);
            return saved.getMembershipId();
        } catch (Exception se) {
            logger.error("Error in adding membership: {}", se.getMessage(), se);
            throw new StudentException("Error in adding membership: " + se.getMessage() + se);
        }
    }

    @Override
    public MembershipDTO getMembershipByStudentId(int studentId) throws StudentException {
        try {
            Membership membership = membershipRepository.findByStudentStudentId(studentId)
                                                        .orElseThrow(() -> new StudentException("Membership not found for Student ID: " + studentId));
            return MembershipMapper.toDTO(membership);
        } catch (Exception se) {
            logger.error("Error retrieving membership for student ID: {}" + studentId + se);
            throw new StudentException("Error retrieving membership for student ID " + studentId + ": " + se.getMessage() + se);
        }
    }    

    @Override
    public List<MembershipDTO> getAllMemberships() throws StudentException {
        try {
            List<Membership> memberships = membershipRepository.findAll();
            return memberships.stream()
                              .map(MembershipMapper::toDTO)
                              .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error retrieving all memberships: {}" + se.getMessage() + se);
            throw new StudentException("Error retrieving all memberships: " + se.getMessage() + se);
        }
    }

    @Override
    public MembershipDTO getMembershipById(int membershipId) throws StudentException {
        try {
            Membership membership = membershipRepository.findById(membershipId)
                                                        .orElseThrow(() -> new StudentException("Membership not found for ID: " + membershipId));
            return MembershipMapper.toDTO(membership);
        } catch (Exception se) {
            logger.error("Error retrieving membership for membership ID: {}" + membershipId + se);
            throw new StudentException("Error retrieving membership for membership ID " + membershipId + ": " + se.getMessage() + se);
        }
    }

    @Override
    public void updateMembership(MembershipDTO membershipDTO) throws StudentException {
        try {
            Membership membership = MembershipMapper.toEntity(membershipDTO);
            membershipRepository.save(membership);
        } catch (Exception se) {
            logger.error("Error updating membership: {}" + se.getMessage() + se);
            throw new StudentException("Error updating membership: " + se.getMessage() + se);
        }
    }

    @Override
    public void deleteMembership(int membershipId) throws StudentException {
        try {
            membershipRepository.deleteById(membershipId);
        } catch (Exception se) {
            logger.error("Error deleting membership: {}" + se.getMessage() + se);
            throw new StudentException("Error deleting membership: " + se.getMessage() + se);
        }
    }
}
