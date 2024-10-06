package com.springboot.academic_system.services;

import com.springboot.academic_system.dtos.CourseRequestDto;
import com.springboot.academic_system.dtos.CourseResponseDto;
import com.springboot.academic_system.models.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

    Page<CourseResponseDto> getAllCourses(Pageable pageable);

    CourseResponseDto getCourseById(Long id);

    Course findCourseById(Long id);

    @Transactional
    CourseResponseDto addCourse(CourseRequestDto courseRequestDto);

    @Transactional
    CourseResponseDto updateCourse(CourseRequestDto courseRequestDto,Long id);

    @Transactional
    void removeCourse(Long id);

}
