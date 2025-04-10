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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an Activity entity.
 *
 * Each Activity has a unique ID, a name, and a type (e.g. Indoor or Outdoor).
 * It also has a many-to-many relationship with Student entities, with the join table "student_activity".
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
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
}
