package com.springboot.academic_system.exceptions;

public class CourseMaxStudentsFullException extends RuntimeException{

    public CourseMaxStudentsFullException(){
    }

    public CourseMaxStudentsFullException(String message){
        super(message);
    }

}
