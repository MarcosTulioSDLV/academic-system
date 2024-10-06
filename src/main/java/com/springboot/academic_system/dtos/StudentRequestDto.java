package com.springboot.academic_system.dtos;

import com.springboot.academic_system.enums.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class StudentRequestDto {

    @NotBlank
    private String document;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Gender gender;

    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

}
