package com.student.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.student.spring.dto.GradeDTO;
import com.student.spring.entity.Grade;
import com.student.spring.exception.StudentException;
import com.student.spring.mapper.GradeMapper;
import com.student.spring.repository.GradeRepository;
import com.student.spring.service.GradeService;

/**
 * Service implementation class for managing grade-related operations.
 *
 * This service handles the core business logic for creating, retrieving,
 * updating, and deleting grade records. It also supports fetching grades
 * associated with a specific student.
 */
@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

    @Autowired
    private GradeRepository gradeRepository;

    /**
     * Adds a new grade.
     *
     * @param gradeDTO the grade data to be added
     * @return the ID of the newly created grade
     * @throws StudentException if the grade cannot be added
     */
    @Override
    public int addGrade(GradeDTO gradeDTO) throws StudentException {
        try {
            Grade grade = GradeMapper.toEntity(gradeDTO);
            Grade updatedGrade = gradeRepository.save(grade);
            return updatedGrade.getGradeId();
        } catch (Exception se) {
            logger.error("Error in adding student grade", se);
            throw new StudentException("Error in adding student grade: " + se.getMessage());
        }
    }

    /**
     * Retrieves all grades associated with a specific student.
     *
     * @param studentId the student ID to filter grades
     * @return a list of GradeDTOs associated with the student
     * @throws StudentException if grades cannot be retrieved
     */
    @Override
    public List<GradeDTO> getGradesByStudentId(int studentId) throws StudentException {
        try {
            List<Grade> grades = gradeRepository.findByStudentsStudentId(studentId);
            return grades.stream()
                         .map(GradeMapper::toDTO)
                         .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error in fetching student grade", se);
            throw new StudentException("Error in fetching student grade: " + se.getMessage());
        }
    }

    /**
     * Retrieves all grades in the system.
     *
     * @return a list of all GradeDTOs
     * @throws StudentException if grades cannot be retrieved
     */
    @Override
    public List<GradeDTO> getAllGrades() throws StudentException {
        try {
            List<Grade> grades = gradeRepository.findAll();
            return grades.stream()
                         .map(GradeMapper::toDTO)
                         .collect(Collectors.toList());
        } catch (Exception se) {
            logger.error("Error in fetching all grades", se);
            throw new StudentException("Error in fetching all grades: " + se.getMessage());
        }
    }

    /**
     * Updates an existing grade.
     *
     * @param gradeDTO the updated grade data
     * @throws StudentException if the grade cannot be updated
     */
    @Override
    public void updateGrade(GradeDTO gradeDTO) throws StudentException {
        try {
            Grade grade = GradeMapper.toEntity(gradeDTO);
            gradeRepository.save(grade);
        } catch (Exception se) {
            logger.error("Error in updating student grade", se);
            throw new StudentException("Error in updating student grade: " + se.getMessage());
        }
    }

    /**
     * Deletes a grade by its ID.
     *
     * @param gradeId the ID of the grade to delete
     * @throws StudentException if the grade cannot be deleted
     */
    @Override
    public void deleteGrade(int gradeId) throws StudentException {
        try {
            gradeRepository.deleteById(gradeId);
        } catch (Exception se) {
            logger.error("Error in deleting student grade", se);
            throw new StudentException("Error in deleting student grade: " + se.getMessage());
        }
    }
}

































// package com.student.spring.service.impl;

// import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.student.spring.entity.Grade;
// import com.student.spring.exception.StudentException;
// import com.student.spring.repository.GradeRepository;
// import com.student.spring.service.GradeService;

// @Service
// @Transactional
// public class GradeServiceImpl implements GradeService {

//     private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);
    
//     private final GradeRepository gradeRepository;
    
//     public GradeServiceImpl(GradeRepository gradeRepository) {
//         this.gradeRepository = gradeRepository;
//     }
    
//     @Override
//     public int addGrade(Grade grade) throws StudentException {
//         try {
//             Grade saved = gradeRepository.save(grade);
//             return saved.getGradeId();
//         } catch (Exception se) {
//             logger.error("Error in adding student grade"+ se);
//             throw new StudentException("Error in adding student grade: " + se.getMessage());
//         }
//     }
    
//     @Override
//     public int addGradeForStudent(Grade grade, int studentId) throws StudentException {
//         try {
//             Grade saved = gradeRepository.save(grade);
//             return saved.getGradeId();
//         } catch (Exception se) {
//             logger.error("Error in adding grade for student"+ se);
//             throw new StudentException("Error in adding grade for student: " + se.getMessage());
//         }
//     }
    
//     @Override
//     public List<Grade> getGradesByStudentId(int studentId) throws StudentException {
//         try {
//             List<Grade> grades = gradeRepository.findByStudentsStudentId(studentId);
//             return grades;
//         } catch (Exception se) {
//             logger.error("Error in fetching student grade"+ se);
//             throw new StudentException("Error in fetching student grade: " + se.getMessage());
//         }
//     }
    
//     @Override
//     public List<Grade> getAllGrades() throws StudentException {
//         try {
//             List<Grade> grades = gradeRepository.findAll();
//             return grades;
//         } catch (Exception se) {
//             logger.error("Error in fetching all grades"+ se);
//             throw new StudentException("Error in fetching all grades: " + se.getMessage());
//         }
//     }
    
//     @Override
//     public void updateGrade(Grade grade) throws StudentException {
//         try {
//             gradeRepository.save(grade);
//         } catch (Exception se) {
//             logger.error("Error in updating student grade"+ se);
//             throw new StudentException("Error in updating student grade: " + se.getMessage());
//         }
//     }
    
//     @Override
//     public void deleteGrade(int gradeId) throws StudentException {
//         try {
//             gradeRepository.deleteById(gradeId);
//         } catch (Exception se) {
//             logger.error("Error in deleting student grade"+ se);
//             throw new StudentException("Error in deleting student grade: " + se.getMessage());
//         }
//     }
// }
