package com.student.spring.mapper;

import com.student.spring.dto.ActivityDTO;
import com.student.spring.entity.Activity;

public class ActivityMapper {

    public static ActivityDTO toDTO(Activity activity) {
        if (activity == null) {
            return null;
        }
        ActivityDTO dto = new ActivityDTO();
        dto.setActivityId(activity.getActivityId());
        dto.setActivityName(activity.getActivityName());
        dto.setActivityType(activity.getActivityType());
        return dto;
    }

    public static Activity toEntity(ActivityDTO dto) {
        if (dto == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setActivityId(dto.getActivityId());
        activity.setActivityName(dto.getActivityName());
        activity.setActivityType(dto.getActivityType());
        return activity;
    }
}
