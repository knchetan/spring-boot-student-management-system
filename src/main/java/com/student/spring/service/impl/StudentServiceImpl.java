package com.student.spring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.student.spring.entity.Student;
import com.student.spring.exception.StudentException;
import com.student.spring.repository.StudentRepository;
import com.student.spring.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    
    private final StudentRepository studentRepository;
    
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    @Override
    public int registerStudent(Student student) throws StudentException {
        try {
            Student saved = studentRepository.save(student);
            return saved.getStudentId();
        } catch (Exception se) {
            logger.error("Error in registering student", se);
            throw new StudentException("Error in registering student: " + se.getMessage() + se);
        }
    }
    
    @Override
    public List<Student> getAllStudents() throws StudentException {
        try {
            List<Student> students = studentRepository.findAll();
            return students;
        } catch (Exception se) {
            logger.error("Error fetching student records", se);
            throw new StudentException("Error fetching student records: " + se.getMessage() + se);
        }
    }
    
    @Override
    public Student getStudentById(int studentId) throws StudentException {
        try {
            Student student = studentRepository.findById(studentId).orElse(null);
            return student;
        } catch (Exception se) {
            logger.error("Error fetching student with ID: {}", studentId, se);
            throw new StudentException("Error fetching student with ID " + studentId + ": " + se.getMessage() + se);
        }
    }
    
    @Override
    public boolean isStudentExists(int studentId) throws StudentException {
        try {
            boolean exists = studentRepository.existsById(studentId);
            return exists;
        } catch (Exception se) {
            logger.error("Error checking if student exists with ID: {}", studentId, se);
            throw new StudentException("Error checking if student exists with ID " + studentId + ": " + se.getMessage() + se);
        }
    }
    
    @Override
    public void updateStudent(Student student) throws StudentException {
        try {
            studentRepository.save(student);
        } catch (Exception se) {
            logger.error("Error updating student records", se);
            throw new StudentException("Error updating student records: " + se.getMessage() + se);
        }
    }
    
    @Override
    public void deleteStudent(int studentId) throws StudentException {
        try {
            studentRepository.deleteById(studentId);
        } catch (Exception se) {
            logger.error("Error deleting student records", se);
            throw new StudentException("Error deleting student records: " + se.getMessage() + se);
        }
    }
}
