package com.student.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.spring.dto.ActivityDTO;
import com.student.spring.entity.Activity;
import com.student.spring.exception.StudentException;
import com.student.spring.mapper.ActivityMapper;
import com.student.spring.repository.ActivityRepository;
import com.student.spring.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Override
    public int addActivity(ActivityDTO activityDTO) throws StudentException {
        validateDuplicateActivity(activityDTO, false);
    
        Activity activity = ActivityMapper.toEntity(activityDTO);
        Activity saved = activityRepository.save(activity);
        return saved.getActivityId();
    }
    
    
    @Override
    public List<ActivityDTO> getAllActivities() throws StudentException {
        try {
            List<Activity> activities = activityRepository.findAll();
            return activities.stream()
                             .map(ActivityMapper::toDTO)
                             .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error fetching activities: {}"+ se.getMessage()+ se);
            throw new StudentException("Error fetching activities: " + se.getMessage());
        }
    }
    
    @Override
    public void updateActivity(ActivityDTO activityDTO) throws StudentException {
        validateDuplicateActivity(activityDTO, true);
    
        Activity activity = ActivityMapper.toEntity(activityDTO);
        activityRepository.save(activity);
    }
    
    @Override
    public void deleteActivity(int activityId) throws StudentException {
        try {
            activityRepository.deleteById(activityId);
        } catch (Exception se) {
            logger.error("Error deleting activity: {}"+ se.getMessage()+ se);
            throw new StudentException("Error deleting activity: " + se.getMessage());
        }
    }
    
    @Override
    public List<ActivityDTO> getActivitiesByStudentId(int studentId) throws StudentException {
        try {
            List<Activity> activities = activityRepository.findByStudentsStudentId(studentId);
            return activities.stream()
                             .map(ActivityMapper::toDTO)
                             .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error retrieving activities for student ID {}: {}", studentId+ se.getMessage()+ se);
            throw new StudentException("Error retrieving activities for student ID " + studentId + ": " + se.getMessage());
        }
    }

    /**
     * Extracts the last word (suffix) from the given activity type.
     *
     * @param activityType the full activity type string (e.g., "Indoor Activity")
     * @return the last word of the activity type (e.g., "Activity")
     */
    private String extractSuffix(String activityType) {
        String[] words = activityType.trim().split("\\s+");
        return words[words.length - 1];
    }

    /**
     * Validates that an activity is not a duplicate before saving or updating.
     * Duplicate validation is based on:
     * 1. Exact match of activity name.
     * 2. Match of both activity name and type suffix.
     *
     * @param dto the activity DTO being added or updated
     * @param isUpdate true if called during update (to exclude self-check)
     * @throws StudentException if a duplicate activity is found
     */

    private void validateDuplicateActivity(ActivityDTO dto, boolean isUpdate) throws StudentException {
        String newName = dto.getActivityName().trim().toLowerCase();
        String newTypeSuffix = extractSuffix(dto.getActivityType().trim().toLowerCase());
    
        List<Activity> existingActivities = activityRepository.findAll();
    
        for (Activity act : existingActivities) {
            if (isUpdate && act.getActivityId() == dto.getActivityId()) continue;
    
            String existingName = act.getActivityName().trim().toLowerCase();
            String existingTypeSuffix = extractSuffix(act.getActivityType().trim().toLowerCase());
    
            if (existingName.equals(newName)) {
                throw new StudentException("Activity already exists with name: " + newName);
            }
    
            if (existingName.equals(newName) && existingTypeSuffix.equals(newTypeSuffix)) {
                throw new StudentException("Activity with name '" + newName + "' and type suffix '" + newTypeSuffix + "' already exists.");
            }
        }
    }
    
}
