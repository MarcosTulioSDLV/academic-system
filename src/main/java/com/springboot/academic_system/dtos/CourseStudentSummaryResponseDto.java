package com.springboot.academic_system.dtos;

import com.springboot.academic_system.models.Course;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CourseStudentSummaryResponseDto {

    private Course course;

    private Double score;

}
