package com.springboot.academic_system.dtos;

import com.springboot.academic_system.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class StudentResponseDto {

    private Long id;

    private String document;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate birthDate;

    private Integer age;

}
