package com.student.spring.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {
    private int membershipId;
    private Date startDate;
    private Date expiryDate;
    
    @NotBlank(message = "Membership type is required")
    private String membershipType;
}