package com.student.spring.entity;

import java.sql.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a membership for a student.
 * Each student has one membership and vice versa.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "membership")
public class Membership {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "membershipId")
        private int membershipId;

        @Column(name = "startDate", nullable = false) 
        private Date startDate;

        @Column(name = "expiryDate", nullable = false) 
        private Date expiryDate;

        @Column(name = "membershipType", nullable = false) 
        private String membershipType;

        /**
         * One membership can be assigned to one student only.
         * Cascade is set to ALL and fetch is EAGER to mimic lazy="false" with fetch="select"
         */        
        @OneToOne(mappedBy = "membership", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Student student;
}
