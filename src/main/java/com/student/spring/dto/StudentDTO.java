package com.student.spring.dto;

import java.sql.Date;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private int studentId;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phone;
    
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Date of Birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    
    @Valid
    @NotNull(message = "Membership is required")
    private MembershipDTO membership;
    
    @Valid
    @NotNull(message = "Grade is required")
    private GradeDTO grade;
    
    @Valid
    @NotEmpty(message = "At least one activity is required")
    private Set<ActivityDTO> activities;
}
