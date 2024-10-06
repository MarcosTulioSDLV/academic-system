package com.springboot.academic_system.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CourseStudentRequestDto {

    @NotNull @Positive
    Long courseId;

    @NotNull @Positive
    Long studentId;

}
