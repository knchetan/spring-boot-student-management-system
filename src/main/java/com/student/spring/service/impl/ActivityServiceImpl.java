package com.student.spring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.student.spring.entity.Activity;
import com.student.spring.exception.StudentException;
import com.student.spring.repository.ActivityRepository;
import com.student.spring.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);
    
    private final ActivityRepository activityRepository;
    
    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
    
    /**
     * Registers a new activity in the database and returns the generated activity ID.
     *
     * @param activity the Activity object containing the activity details.
     * @return the generated activity ID.
     * @throws StudentException if registration fails.
     */
    @Override
    public int addActivity(Activity activity) throws StudentException {
        try {
            Activity savedActivity = activityRepository.save(activity);
            return savedActivity.getActivityId();
        } catch (Exception se) {
            logger.error("Error in adding activity" + se);
            throw new StudentException("Error in adding activity: " + se.getMessage());
        }
    }
    
    /**
     * Retrieves the list of Activity objects associated with a given student ID.
     *
     * @param studentId the ID of the student.
     * @return a list of Activity objects.
     * @throws StudentException if an error occurs during retrieval.
     */
    @Override
    public List<Activity> getActivitiesByStudentId(int studentId) throws StudentException {
        try {
            List<Activity> activities = activityRepository.findByStudentsStudentId(studentId);
            return activities;
        } catch (Exception se) {
            logger.error("Error retrieving activities for student ID: {}" + studentId + se);
            throw new StudentException("Error retrieving activities for student ID " + studentId + ": " + se.getMessage());
        }
    }
    
    /**
     * Retrieves all activity records.
     *
     * @return a list of Activity objects.
     * @throws StudentException if retrieval fails.
     */
    @Override
    public List<Activity> getAllActivities() throws StudentException {
        try {
            List<Activity> activities = activityRepository.findAll();
            return activities;
        } catch (Exception se) {
            logger.error("Error fetching activities" + se);
            throw new StudentException("Error fetching activities: " + se.getMessage());
        }
    }
    
    /**
     * Updates an existing activity record.
     *
     * @param activity the Activity object with updated details.
     * @throws StudentException if an error occurs during update.
     */
    @Override
    public void updateActivity(Activity activity) throws StudentException {
        try {
            activityRepository.save(activity);
        } catch (Exception se) {
            logger.error("Error updating activity" + se);
            throw new StudentException("Error updating activity: " + se.getMessage() + se);
        }
    }
    
    /**
     * Deletes an activity record based on the activity ID.
     *
     * @param activityId the activity ID to delete.
     * @throws StudentException if an error occurs during deletion.
     */
    @Override
    public void deleteActivity(int activityId) throws StudentException {
        try {
            activityRepository.deleteById(activityId);
        } catch (Exception se) {
            logger.error("Error deleting activity" + se);
            throw new StudentException("Error deleting activity: " + se.getMessage());
        }
    }
}
