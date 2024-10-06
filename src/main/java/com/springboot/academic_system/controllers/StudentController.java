package com.springboot.academic_system.controllers;

import com.springboot.academic_system.dtos.StudentRequestDto;
import com.springboot.academic_system.dtos.StudentResponseDto;
import com.springboot.academic_system.services.StudentService;
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
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/students")
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @GetMapping(value = "/students-by-id/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping(value = "/students")
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody @Valid StudentRequestDto studentRequestDto){
        return new ResponseEntity<>(studentService.addStudent(studentRequestDto),HttpStatus.CREATED);
    }

    @PutMapping(value = "/students/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@RequestBody @Valid StudentRequestDto studentRequestDto,
                                                            @PathVariable Long id){
        return ResponseEntity.ok(studentService.updateStudent(studentRequestDto,id));
    }

    @DeleteMapping(value = "/students/{id}")
    public ResponseEntity<Object> removeStudent(@PathVariable Long id){
        studentService.removeStudent(id);
        return ResponseEntity.ok("Removed Student with id: "+id+" successfully!");
    }

}
