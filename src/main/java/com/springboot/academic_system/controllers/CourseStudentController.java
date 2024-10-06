package com.springboot.academic_system.controllers;

import com.springboot.academic_system.dtos.*;
import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.models.Student;
import com.springboot.academic_system.services.CourseStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CourseStudentController {

    private final CourseStudentService courseStudentService;

    @Autowired
    public CourseStudentController(CourseStudentService courseStudentService) {
        this.courseStudentService = courseStudentService;
    }


    @GetMapping(value = "/course-students-by-id")
    public ResponseEntity<CourseStudentResponseDto> getCourseStudentById(@RequestBody @Valid CourseStudentRequestDto courseStudentRequestDto){
        return ResponseEntity.ok(courseStudentService.getCourseStudentById(courseStudentRequestDto));
    }

    //Note: Return the courses, but not other data such as scores.
    @GetMapping(value = "/course-students-by-student-id/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(courseStudentService.getCoursesByStudentId(studentId));
    }

    //Note: In addition to returning the courses, also return the scores (it could return another attributes of the CourseStudent class).
    //Note: A CourseStudentSummaryResponseDto was required, because not all attributes of CourseStudentResponseDto are returned.
    @GetMapping(value = "/course-students-summary-by-student-id/{studentId}")
    public ResponseEntity<List<CourseStudentSummaryResponseDto>> getCourseStudentSummaryByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(courseStudentService.getCourseStudentSummaryByStudentId(studentId));
    }

    @GetMapping(value = "/course-students-by-course-id/{courseId}")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable Long courseId){
        return ResponseEntity.ok(courseStudentService.getStudentsByCourseId(courseId));
    }

    @PostMapping(value = "/course-students")
    public ResponseEntity<List<CourseStudentResponseDto>> addCourseStudent(@RequestBody @Valid AddCourseStudentRequestDto addCourseStudentRequestDto,
                                                                           @PageableDefault(size = 10)Pageable pageable){
        return new ResponseEntity<>(courseStudentService.addCourseStudent(addCourseStudentRequestDto),HttpStatus.CREATED);
    }

    @PutMapping(value = "/course-students-score")
    public ResponseEntity<CourseStudentResponseDto> setCourseStudentScore(@RequestBody @Valid CourseStudentScoreRequestDto courseStudentScoreRequestDto){
        return ResponseEntity.ok(courseStudentService.setCourseStudentScore(courseStudentScoreRequestDto));
    }

    @DeleteMapping(value = "/course-students")
    public ResponseEntity<Object> removeCourseStudent(@RequestBody @Valid CourseStudentRequestDto courseStudentRequestDto){
        courseStudentService.removeCourseStudent(courseStudentRequestDto);
        String message= String.format("Course x Student: (Course id: %d, Student id: %d) removed successfully!",courseStudentRequestDto.getCourseId(),courseStudentRequestDto.getStudentId());
        return ResponseEntity.ok(message);
    }

}
