package com.student.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private int gradeId;

    @NotBlank(message = "Grade is required")
    private String grade;
    
    @NotNull(message = "Standard is required")
    private int standard;
}
