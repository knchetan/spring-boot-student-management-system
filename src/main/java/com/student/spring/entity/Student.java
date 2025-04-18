package com.student.spring.entity;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *      Represents a student entity.
 *    
 *      Each student is linked to a Grade (which includes the grade and standard),
 *      a Membership, and a set of Activities. In addition, the student has personal details such as
 *      first name, last name, phone number, email, address, and date of birth. The age is computed
 *      based on the date of birth.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int studentId;
        
        @Column(name = "first_name", nullable = false)
        private String firstName;

        @Column(name = "last_name", nullable = false)
        private String lastName;

        @Column(name = "phone_no", nullable = false)
        private String phoneNo;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "address", nullable = false)
        private String address;

        @Column(name = "dob", nullable = false)
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date dob;

        // One-to-One association to Membership (exclusive to a student)
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "membership_id")
	private Membership membership;

        // Many-to-One association to Grade (shared among students)
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "grade_id")
	private Grade grade;

        // Many-to-Many association with Activity
        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinTable(name = "student_activity",
                joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "activity_id"))
	private Set<Activity> activity;              
}
