package com.springboot.academic_system.exceptions;

public class CourseCodeExistsException extends RuntimeException{

    public CourseCodeExistsException(){
    }

    public CourseCodeExistsException(String message){
        super(message);
    }

}
