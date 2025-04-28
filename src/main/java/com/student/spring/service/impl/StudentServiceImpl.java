package com.student.spring.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.student.spring.dto.StudentDTO;
import com.student.spring.dto.StudentInputDTO;
import com.student.spring.entity.Activity;
import com.student.spring.entity.Grade;
import com.student.spring.entity.Membership;
import com.student.spring.entity.Student;
import com.student.spring.exception.StudentException;
import com.student.spring.mapper.StudentMapper;
import com.student.spring.repository.ActivityRepository;
import com.student.spring.repository.GradeRepository;
import com.student.spring.repository.MembershipRepository;
import com.student.spring.repository.StudentRepository;
import com.student.spring.service.StudentService;

/**
 * Service implementation class for managing student-related operations.
 * 
 * This service handles the core business logic for registering, retrieving,
 * updating, and deleting student records. It also supports checking whether a
 * student exists by ID.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Registers a new student.
     *
     * @param studentDTO the student data to be saved
     * @return the generated student ID after saving
     * @throws StudentException if registration fails
     */
    @Override
    public int registerStudent(StudentDTO studentDTO) throws StudentException {
        try {
            Student student = StudentMapper.toEntity(studentDTO);
            Student updatedStudent = studentRepository.save(student);
            return updatedStudent.getStudentId();
        } catch (Exception se) {
            logger.error("Error in registering student", se);
            throw new StudentException("Error in registering student: " + se.getMessage());
        }
    }

    /**
     * Retrieves all students from the database.
     *
     * @return a list of StudentDTOs
     * @throws StudentException if retrieval fails
     */
    @Override
    public List<StudentDTO> getAllStudents() throws StudentException {
        try {
            List<Student> students = studentRepository.findAll();
            return students.stream()
                           .map(StudentMapper::toDTO)
                           .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error fetching student records", se);
            throw new StudentException("Error fetching student records: " + se.getMessage());
        }
    }

    /**
     * Retrieves a student by ID.
     *
     * @param studentId the ID of the student to retrieve
     * @return the corresponding StudentDTO, or null if not found
     * @throws StudentException if retrieval fails
     */
    @Override
    public StudentDTO getStudentById(int studentId) throws StudentException {
        try {
            Student student = studentRepository.findById(studentId).orElse(null);
            return StudentMapper.toDTO(student);
        } catch (Exception se) {
            logger.error("Error fetching student with ID: {}", studentId, se);
            throw new StudentException("Error fetching student with ID " + studentId + ": " + se.getMessage());
        }
    }

    /**
     * Checks if a student exists by ID.
     *
     * @param studentId the student ID to check
     * @return true if the student exists, false otherwise
     * @throws StudentException if the check fails
     */
    @Override
    public boolean isStudentExists(int studentId) throws StudentException {
        try {
            return studentRepository.existsById(studentId);
        } catch (Exception se) {
            logger.error("Error checking if student exists with ID: {}", studentId, se);
            throw new StudentException("Error checking if student exists with ID " + studentId + ": " + se.getMessage());
        }
    }

    /**
     * Updates an existing student.
     *
     * @param studentDTO the updated student data
     * @throws StudentException if the update operation fails
     */
    @Override
    public void updateStudent(StudentDTO studentDTO) throws StudentException {
        try {
            Student student = StudentMapper.toEntity(studentDTO);
            studentRepository.save(student);
        } catch (Exception se) {
            logger.error("Error updating student records", se);
            throw new StudentException("Error updating student records: " + se.getMessage());
        }
    }

    /**
     * Deletes a student by ID.
     *
     * @param studentId the ID of the student to delete
     * @throws StudentException if the deletion fails
     */
    @Override
    public void deleteStudent(int studentId) throws StudentException {
        try {
            studentRepository.deleteById(studentId);
        } catch (Exception se) {
            logger.error("Error deleting student records", se);
            throw new StudentException("Error deleting student records: " + se.getMessage());
        }
    }

    /**
     * Registers a new student using StudentInputDTO with flat JSON structure.
     * Fetches related entities by ID and maps them to a Student entity.
     *
     * @param studentInputDTO the student input DTO
     * @return the saved StudentDTO
     * @throws StudentException if related entities are not found or saving fails
     */
    @Override
    public StudentDTO registerStudentFromInput(StudentInputDTO studentInputDTO) throws StudentException {
        try {
            Student student = mapInputToEntity(studentInputDTO);
            Student newStudent = studentRepository.save(student);
            return StudentMapper.toDTO(newStudent);
        } catch (Exception se) {
            logger.error("Error in registering student", se);
            throw new StudentException("Error in registering student: " + se.getMessage() + se);
        }
    }

    /**
     * Updates an existing student using StudentInputDTO with flat JSON structure.
     * Replaces existing values and associations by resolving referenced IDs.
     *
     * @param studentId the ID of the student to update
     * @param studentInputDTO the student input DTO
     * @return the updated StudentDTO
     * @throws StudentException if student or related entities are not found
     */
    @Override
    public StudentDTO updateStudentFromInput(int studentId, StudentInputDTO studentInputDTO) throws StudentException {
        try {
            Student existingStudent = studentRepository.findById(studentId)
                    .orElseThrow(() -> new StudentException("Student not found with ID: " + studentId));
    
            Student inputMapped = mapInputToEntity(studentInputDTO);
    
            existingStudent.setFirstName(inputMapped.getFirstName());
            existingStudent.setLastName(inputMapped.getLastName());
            existingStudent.setPhoneNo(inputMapped.getPhoneNo());
            existingStudent.setEmail(inputMapped.getEmail());
            existingStudent.setAddress(inputMapped.getAddress());
            existingStudent.setDob(inputMapped.getDob());
            existingStudent.setGrade(inputMapped.getGrade());
            existingStudent.setMembership(inputMapped.getMembership());
            existingStudent.setActivities(inputMapped.getActivities());
    
            Student updatedStudent = studentRepository.save(existingStudent);
            return StudentMapper.toDTO(updatedStudent);
    
        } catch (Exception se) {
            logger.error("Error in updating student", se);
            throw new StudentException("Error in updating student: " + se.getMessage());
        }
    }
    

    private Student mapInputToEntity(StudentInputDTO studentInputDTO) throws StudentException {
        Student student = new Student();
        student.setFirstName(studentInputDTO.getFirstName());
        student.setLastName(studentInputDTO.getLastName());
        student.setPhoneNo(studentInputDTO.getPhoneNo());
        student.setEmail(studentInputDTO.getEmail());
        student.setAddress(studentInputDTO.getAddress());
        student.setDob(studentInputDTO.getDob());

        Grade grade = gradeRepository.findById(studentInputDTO.getGradeId())
                .orElseThrow(() -> new StudentException("Grade not found with ID: " + studentInputDTO.getGradeId()));
        student.setGrade(grade);

        Membership membership = membershipRepository.findById(studentInputDTO.getMembershipId())
                .orElseThrow(() -> new StudentException("Membership not found with ID: " + studentInputDTO.getMembershipId()));
        student.setMembership(membership);

        try {
            Set<Activity> activities = studentInputDTO.getActivityIds().stream()
                .map(this::fetchActivityById) 
                .collect(Collectors.toSet());
            student.setActivities(activities);
        } catch (RuntimeException re) {
            if (re.getCause() instanceof StudentException se) {
                throw se;
            }
            throw re;
        }
        return student;
    }

    /**
     * Helper method to fetch an activity by ID or throw a runtime exception if not found.
     *
     * @param activityId the activity ID
     * @return the corresponding Activity entity
     */
    private Activity fetchActivityById(int activityId) {
        try {
            return activityRepository.findById(activityId)
                .orElseThrow(() -> new StudentException("Activity not found with ID: " + activityId));
        } catch (StudentException se) {
            throw new RuntimeException(se);
        }
    }
    
}
