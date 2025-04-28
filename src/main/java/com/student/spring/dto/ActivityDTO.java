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
public class ActivityDTO {
    private int activityId;

    @NotNull(message = "Activity name is required")
    private String activityName;
    
    @NotBlank(message = "Activity type is required")
    private String activityType;
}
