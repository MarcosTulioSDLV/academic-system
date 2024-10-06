package com.springboot.academic_system.exceptions;

public class CourseMaxStudentsLessThanCurrentStudents extends RuntimeException{

    public CourseMaxStudentsLessThanCurrentStudents(){
    }

    public CourseMaxStudentsLessThanCurrentStudents(String message){
        super(message);
    }

}
