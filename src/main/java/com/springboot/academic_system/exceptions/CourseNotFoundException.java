package com.springboot.academic_system.exceptions;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException(){
    }

    public CourseNotFoundException(String message){
        super(message);
    }
}