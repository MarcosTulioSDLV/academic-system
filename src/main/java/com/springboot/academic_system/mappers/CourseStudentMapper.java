package com.springboot.academic_system.mappers;

import com.springboot.academic_system.dtos.CourseStudentRequestDto;
import com.springboot.academic_system.dtos.CourseStudentResponseDto;
import com.springboot.academic_system.dtos.CourseStudentSummaryResponseDto;
import com.springboot.academic_system.models.CourseStudent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseStudentMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CourseStudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CourseStudentResponseDto toCourseStudentResponseDto(CourseStudent courseStudent){
        return modelMapper.map(courseStudent,CourseStudentResponseDto.class);
    }

    public CourseStudentSummaryResponseDto toCourseStudentSummaryResponseDto(CourseStudent courseStudent){
        return modelMapper.map(courseStudent,CourseStudentSummaryResponseDto.class);
    }


}
