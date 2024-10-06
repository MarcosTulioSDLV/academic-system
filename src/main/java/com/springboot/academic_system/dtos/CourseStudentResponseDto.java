package com.springboot.academic_system.dtos;

import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.models.Student;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseStudentResponseDto {

    private Course course;

    private Student student;

    private Double score;

}
