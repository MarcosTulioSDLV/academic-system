package com.springboot.academic_system.controllers;

import com.springboot.academic_system.dtos.CourseRequestDto;
import com.springboot.academic_system.dtos.CourseResponseDto;
import com.springboot.academic_system.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/courses")
    public ResponseEntity<Page<CourseResponseDto>> getAllCourses(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    @GetMapping(value = "/courses-by-id/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping(value = "/courses")
    public ResponseEntity<CourseResponseDto> addCourse(@RequestBody @Valid CourseRequestDto courseRequestDto){
        return new ResponseEntity<>(courseService.addCourse(courseRequestDto),HttpStatus.CREATED);
    }

    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@RequestBody @Valid CourseRequestDto courseRequestDto,
                                                          @PathVariable Long id){
        return ResponseEntity.ok(courseService.updateCourse(courseRequestDto,id));
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<Object> removeCourse(@PathVariable Long id){
        courseService.removeCourse(id);
        return ResponseEntity.ok("Removed Course with id: "+id+" successfully!");
    }
    
}
