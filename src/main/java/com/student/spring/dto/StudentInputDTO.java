package com.student.spring.dto;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Input DTO for accepting student details in flat JSON format.
 * This DTO maps foreign key relationships via IDs.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentInputDTO {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNo;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Date of Birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @NotNull(message = "Grade ID is required")
    private Integer gradeId;

    @NotNull(message = "Membership ID is required")
    private Integer membershipId;

    @NotEmpty(message = "At least one activity ID is required")
    private Set<Integer> activityIds;
}
