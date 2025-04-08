package com.student.spring.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *      Represents a student entity.
 *    
 *      Each student is linked to a Grade (which includes the grade and standard),
 *      a Membership, and a set of Activities. In addition, the student has personal details such as
 *      first name, last name, phone number, email, address, and date of birth. The age is computed
 *      based on the date of birth.
 */

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

        public Student() {}
        
        public Student(String firstName, String lastName, String phoneNo, String email, String address, Date dob, Membership membership, Grade grade, Set<Activity> activity) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.phoneNo = phoneNo;
                this.email = email;
                this.address = address;
                this.dob = dob;
                this.membership = membership;
                this.grade = grade;
                this.activity = activity;
        }

        public Student(String firstName, String lastName, String phoneNo, String email, String address, Date dob) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.phoneNo = phoneNo;
                this.email = email;
                this.address = address;
                this.dob = dob;
        }        
        
        public void setStudentId(int studentId) {
                this.studentId =studentId;
        }
        
        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public void setDob(Date dob) {
                this.dob = dob;
        }        

        public void setGrade(Grade grade) {
                this.grade = grade;
        }  

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public void setActivity(Set<Activity> activity) {
		this.activity = activity;
	}

        public int getStudentId() {
                return studentId;
        }

        public String getFirstName() {
                return firstName;
        }
        
        public String getLastName() {
                return lastName;
        }
        
        public String getPhoneNo() {
                return phoneNo;
        }
        
        public String getEmail() {
                return email;
        }
        
        public String getAddress() {
                return address;
        }
        
        public Date getDob() {
                return dob;
        }         
      
        public Grade getGrade() {
                return grade;
        } 
        
	public Membership getMembership() {
		return membership;
	}

	public Set<Activity> getActivity() {
		return activity;
	}       

        /**
         * Computes the age based on the date of birth and the current date.
         *
         * @return the current age in years.
         */
        public int getAge() {
                if (dob == null) {
                return 0;
                }
                LocalDate birthDate = dob.toLocalDate();
                LocalDate currentDate = LocalDate.now();
                return Period.between(birthDate, currentDate).getYears();
        }
	
        @Override
        public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Student Details:")
                  .append("    ID: ").append(studentId)
                  .append("    First Name: ").append(firstName)
                  .append("    Last Name: ").append(lastName)
                  .append("    Phone No.: ").append(phoneNo)
                  .append("    Email: ").append(email)
                  .append("    Address: ").append(address)
                  .append("    DOB: ").append(dob)
                  .append("    Age: ").append(getAge())
                  .append("    Grade: ").append(grade != null ? grade : "None assigned")
                  .append("    Membership: ").append(membership != null ? membership.getMembershipType() : "None assigned")
                  .append("    Activity: ").append(activity != null ? activity : "None assigned");
                return stringBuilder.toString();
        }
}
