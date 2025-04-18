package com.student.spring.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Integer gradeId;

    @NotBlank(message = "Grade is required")
    private String grade;
    
    @NotNull(message = "Standard is required")
    private int standard;
}
