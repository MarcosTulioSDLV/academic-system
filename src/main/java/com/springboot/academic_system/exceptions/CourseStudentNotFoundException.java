package com.springboot.academic_system.exceptions;

public class CourseStudentNotFoundException extends RuntimeException{

    public CourseStudentNotFoundException(){
    }

    public CourseStudentNotFoundException(String message){
        super(message);
    }

}
