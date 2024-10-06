package com.springboot.academic_system.services;

import com.springboot.academic_system.dtos.StudentRequestDto;
import com.springboot.academic_system.dtos.StudentResponseDto;
import com.springboot.academic_system.models.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Page<StudentResponseDto> getAllStudents(Pageable pageable);

    StudentResponseDto getStudentById(Long id);

    Student findStudentById(Long id);

    @Transactional
    StudentResponseDto addStudent(StudentRequestDto studentRequestDto);

    @Transactional
    StudentResponseDto updateStudent(StudentRequestDto studentRequestDto,Long id);

    @Transactional
    void removeStudent(Long id);

}
