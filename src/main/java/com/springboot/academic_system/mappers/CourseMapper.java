package com.springboot.academic_system.mappers;

import com.springboot.academic_system.dtos.CourseRequestDto;
import com.springboot.academic_system.dtos.CourseResponseDto;
import com.springboot.academic_system.models.Course;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CourseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CourseResponseDto toCourseResponseDto(Course course){
        return modelMapper.map(course,CourseResponseDto.class);
    }

    public Course toCourse(CourseRequestDto courseRequestDto){
        return modelMapper.map(courseRequestDto,Course.class);
    }

}
