package com.student.spring.mapper;

import com.student.spring.dto.MembershipDTO;
import com.student.spring.entity.Membership;

public class MembershipMapper {

    public static MembershipDTO toDTO(Membership membership) {
        if (membership == null) {
            return null;
        }
        MembershipDTO dto = new MembershipDTO();
        dto.setMembershipId(membership.getMembershipId());
        dto.setStartDate(membership.getStartDate());
        dto.setExpiryDate(membership.getExpiryDate());
        dto.setMembershipType(membership.getMembershipType());
        return dto;
    }

    public static Membership toEntity(MembershipDTO dto) {
        if (dto == null) {
            return null;
        }
        Membership membership = new Membership();
        membership.setMembershipId(dto.getMembershipId());
        membership.setStartDate(dto.getStartDate());
        membership.setExpiryDate(dto.getExpiryDate());
        membership.setMembershipType(dto.getMembershipType());
        return membership;
    }
}
