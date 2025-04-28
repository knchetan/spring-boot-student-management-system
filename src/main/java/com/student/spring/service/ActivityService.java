package com.student.spring.service;

import java.util.List;
import com.student.spring.dto.ActivityDTO;
import com.student.spring.exception.StudentException;

/**
 * Defines the contract for business operations related to Activity entities.
 */
public interface ActivityService {
    /**
     * Registers a new activity in the database.
     *
     * @param activity the Activity object containing the activity details.
     * @return the generated activity ID.
     * @throws StudentException if registration fails.
     */
    int addActivity(ActivityDTO activityDTO) throws StudentException;

    /**
     * Retrieves the list of Activity objects associated with a given student ID.
     *
     * @param studentId the ID of the student.
     * @return a List of Activity objects.
     * @throws StudentException if an error occurs during retrieval.
     */
    List<ActivityDTO> getActivitiesByStudentId(int studentId) throws StudentException;

    /**
     * Retrieves all activity records.
     *
     * @return a list of Activity objects.
     * @throws StudentException if retrieval fails.
     */
    List<ActivityDTO> getAllActivities() throws StudentException;

    /**
     * Updates an existing activity record.
     *
     * @param activity the Activity object with updated details.
     * @throws StudentException if an error occurs during update.
     */
    void updateActivity(ActivityDTO activityDTO) throws StudentException;

    /**
     * Deletes an activity record based on the activity ID.
     *
     * @param activityId the activity ID to delete.
     * @throws StudentException if an error occurs during deletion.
     */
    void deleteActivity(int activityId) throws StudentException;
}
