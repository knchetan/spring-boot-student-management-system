package com.student.spring.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Represents an Activity entity.
 * <p>
 * Each Activity has a unique ID, a name, and a type (e.g. Indoor or Outdoor).
 * It also has a many-to-many relationship with Student entities, with the join table "student_activity".
 * </p>
 */
@Entity
@Table(name = "activity")
public class Activity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "activity_id")
        private Integer activityId;

        @Column(name = "activity_name", nullable = false)
        private String activityName;

        @Column(name = "activity_type", nullable = false)
        private String activityType;

        /**
         * The many-to-many association with Student.
         * Cascade is set to ALL and fetch is EAGER (similar to lazy="false" with a join fetch in XML).
         */
        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinTable(name = "student_activity",
                joinColumns = @JoinColumn(name = "activity_id"),
                inverseJoinColumns = @JoinColumn(name = "student_id")
        )
        private Set<Student> students;

        public Activity() {}

        public Activity(String activityName, String activityType) {
                this.activityName = activityName;
                this.activityType = activityType;
        }

        public int getActivityId() {
                return activityId;
        }

        public void setActivityId(int activityId) {
                this.activityId = activityId;
        }

        public String getActivityName() {
                return activityName;
        }

        public void setActivityName(String activityName) {
                this.activityName = activityName;
        }

        public String getActivityType() {
                return activityType;
        }

        public void setActivityType(String activityType) {
                this.activityType = activityType;
        }

        public Set<Student> getStudents () {
                return students;
        }

        public void setStudents(Set<Student> students) {
                this.students = students;
        }

        @Override
        public String toString() {
                return "Activity Name: " + activityName + ",\tActivity Type: " + activityType;
        }
}
