package com.student.spring.service.impl;

import com.student.spring.dto.StudentDTO;
import com.student.spring.entity.Student;
import com.student.spring.exception.StudentException;
import com.student.spring.mapper.StudentMapper;
import com.student.spring.repository.StudentRepository;
import com.student.spring.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    
    @Autowired
    private StudentRepository studentRepository;
        
    @Override
    public int registerStudent(StudentDTO studentDTO) throws StudentException {
        try {
            Student student = StudentMapper.toEntity(studentDTO);
            Student updatedStudent = studentRepository.save(student);
            return updatedStudent.getStudentId();
        } catch (Exception se) {
            logger.error("Error in registering student"+ se);
            throw new StudentException("Error in registering student: " + se.getMessage() + se);
        }
    }
    
    @Override
    public List<StudentDTO> getAllStudents() throws StudentException {
        try {
            List<Student> students = studentRepository.findAll();
            return students.stream()
                           .map(StudentMapper::toDTO)
                           .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error fetching student records"+ se);
            throw new StudentException("Error fetching student records: " + se.getMessage() + se);
        }
    }
    
    @Override
    public StudentDTO getStudentById(int studentId) throws StudentException {
        try {
            Student student = studentRepository.findById(studentId).orElse(null);
            return StudentMapper.toDTO(student);
        } catch (Exception se) {
            logger.error("Error fetching student with ID: {}" + studentId + se);
            throw new StudentException("Error fetching student with ID " + studentId + ": " + se.getMessage() + se);
        }
    }
    
    @Override
    public boolean isStudentExists(int studentId) throws StudentException {
        try {
            boolean exists = studentRepository.existsById(studentId);
            return exists;
        } catch (Exception se) {
            logger.error("Error checking if student exists with ID: {}" + studentId + se);
            throw new StudentException("Error checking if student exists with ID " + studentId + ": " + se.getMessage() + se);
        }
    }
    
    @Override
    public void updateStudent(StudentDTO studentDTO) throws StudentException {
        try {
            Student student = StudentMapper.toEntity(studentDTO);
            studentRepository.save(student);
        } catch (Exception se) {
            logger.error("Error updating student records"+ se);
            throw new StudentException("Error updating student records: " + se.getMessage() + se);
        }
    }
    
    @Override
    public void deleteStudent(int studentId) throws StudentException {
        try {
            studentRepository.deleteById(studentId);
        } catch (Exception se) {
            logger.error("Error deleting student records"+ se);
            throw new StudentException("Error deleting student records: " + se.getMessage() + se);
        }
    }
}
