package com.springboot.academic_system.services;

import com.springboot.academic_system.dtos.*;
import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.models.Student;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseStudentService {

    CourseStudentResponseDto getCourseStudentById(CourseStudentRequestDto courseStudentRequestDto);

    List<Course> getCoursesByStudentId(Long studentId);

    List<CourseStudentSummaryResponseDto> getCourseStudentSummaryByStudentId(Long studentId);

    List<Student> getStudentsByCourseId(Long courseId);

    @Transactional
    List<CourseStudentResponseDto> addCourseStudent(AddCourseStudentRequestDto addCourseStudentRequestDto);

    @Transactional
    CourseStudentResponseDto setCourseStudentScore(CourseStudentScoreRequestDto courseStudentScoreRequestDto);

    @Transactional
    void removeCourseStudent(CourseStudentRequestDto courseStudentRequestDto);

}
