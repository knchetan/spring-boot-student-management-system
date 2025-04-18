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
public class ActivityDTO {
    private Integer activityId;

    @NotBlank(message = "Activity name is required")
    private String activityName;
    
    @NotBlank(message = "Activity type is required")
    private String activityType;
}
