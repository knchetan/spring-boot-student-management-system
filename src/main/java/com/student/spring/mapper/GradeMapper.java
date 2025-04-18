package com.student.spring.mapper;

import com.student.spring.dto.GradeDTO;
import com.student.spring.entity.Grade;

public class GradeMapper {

    public static GradeDTO toDTO(Grade grade) {
        if (grade == null) {
            return null;
        }
        GradeDTO dto = new GradeDTO();
        dto.setGradeId(grade.getGradeId());
        dto.setGrade(grade.getGrade());
        dto.setStandard(grade.getStandard());
        return dto;
    }
    
    public static Grade toEntity(GradeDTO dto) {
        if (dto == null) {
            return null;
        }
        Grade grade = new Grade();
        grade.setGradeId(dto.getGradeId());
        grade.setGrade(dto.getGrade());
        grade.setStandard(dto.getStandard());
        return grade;
    }
}
