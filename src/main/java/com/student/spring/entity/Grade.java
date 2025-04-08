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

/**
 * Represents a Grade entity.
 * <p>
 * Each Grade includes a grade letter and a standard (class level). 
 * A Grade can be associated with multiple Student entities.
 * This is the inverse side of the many-to-one relationship defined in the Student entity.
 * </p>
 */
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
        
        public Grade() {}

        public Grade(String grade, int standard) {
                this.grade = grade;
                this.standard = standard;
        }        

        public int getGradeId() {
                return gradeId;
        }

        public void setGradeId(int gradeId) {
                this.gradeId = gradeId;
        }

        public String getGrade() {
                return grade;
        }

        public void setGrade(String grade) {
                this.grade = grade;
        }

        public int getStandard() {
                return standard;
        }

        public void setStandard(int standard) {
                this.standard = standard;
        }

        public Set<Student> getStudents() {
                return students;
        }

        public void setStudents(Set<Student> students) {
                this.students = students;
        }

        @Override
        public String toString() {
                return grade + "\tStandard: " + standard;
        }
}
