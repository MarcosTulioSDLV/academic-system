package com.springboot.academic_system.mappers;

import com.springboot.academic_system.dtos.StudentRequestDto;
import com.springboot.academic_system.dtos.StudentResponseDto;
import com.springboot.academic_system.models.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StudentResponseDto toStudentResponseDto(Student student){
        return modelMapper.map(student,StudentResponseDto.class);
    }

    public Student toStudent(StudentRequestDto studentRequestDto){
        return modelMapper.map(studentRequestDto,Student.class);
    }

}
