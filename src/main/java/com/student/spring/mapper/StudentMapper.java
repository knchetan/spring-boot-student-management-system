package com.student.spring.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.student.spring.dto.ActivityDTO;
import com.student.spring.dto.StudentDTO;
import com.student.spring.entity.Activity;
import com.student.spring.entity.Student;

public class StudentMapper {

    public static StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setPhone(student.getPhoneNo());
        dto.setEmail(student.getEmail());
        dto.setAddress(student.getAddress());
        dto.setDob(student.getDob());
        dto.setMembership(MembershipMapper.toDTO(student.getMembership()));
        dto.setGrade(GradeMapper.toDTO(student.getGrade()));
        dto.setActivities(toDTOSet(student.getActivities()));
        return dto;
    }

    public static Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }
        Student student = new Student();
        student.setStudentId(dto.getStudentId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPhoneNo(dto.getPhone());
        student.setEmail(dto.getEmail());
        student.setAddress(dto.getAddress());
        student.setDob(dto.getDob());
        student.setMembership(MembershipMapper.toEntity(dto.getMembership()));
        student.setGrade(GradeMapper.toEntity(dto.getGrade()));
        student.setActivities(toEntitySet(dto.getActivities()));
        return student;
    }

    private static Set<ActivityDTO> toDTOSet(Set<Activity> activities) {
        if (activities == null) {
            return null;
        }
        return activities.stream()
                         .map(ActivityMapper::toDTO)
                         .collect(Collectors.toSet());
    }

    private static Set<Activity> toEntitySet(Set<ActivityDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                   .map(ActivityMapper::toEntity)
                   .collect(Collectors.toSet());
    }
}

