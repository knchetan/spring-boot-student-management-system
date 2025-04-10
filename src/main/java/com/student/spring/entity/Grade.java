package com.student.spring.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Grade entity.
 * <p>
 * Each Grade includes a grade letter and a standard (class level). 
 * A Grade can be associated with multiple Student entities.
 * This is the inverse side of the many-to-one relationship defined in the Student entity.
 * </p>
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grade")
public class Grade {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "grade_id")
        private Integer gradeId;

        @Column(name = "grade", nullable = false)
        private String grade;

        @Column(name = "standard", nullable = false)
        private int standard;

        /**
         * One grade can be assigned to multiple students.
         * Cascade is set to ALL and fetch is EAGER to mimic lazy="false" with fetch="select"
         */
        @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Set<Student> students;
}
