package com.springboot.academic_system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.academic_system.exceptions.CourseMaxStudentsFullException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_COURSE")
@AllArgsConstructor
@Getter @Setter @ToString(exclude = "courseStudentList")
//@EqualsAndHashCode(exclude = "courseStudentList")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String courseCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer credits;

    @Column(nullable = false)
    private Integer maxStudents;

    @Column(nullable = false)
    private Integer currentStudents;

    @JsonIgnore
    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    private List<CourseStudent> courseStudentList= new ArrayList<>();

    public Course(){
    }

    private boolean canEnrollMoreStudents(){
        return currentStudents<maxStudents;
    }

    public void enrollStudent() {
        if(!canEnrollMoreStudents()){
            throw new CourseMaxStudentsFullException("The Max Students for the Course: "+this+" is full");
        }
        currentStudents++;
    }

    public void unenrollStudent(){
        currentStudents--;
    }

}
