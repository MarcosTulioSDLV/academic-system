package com.springboot.academic_system.exceptions;

public class CourseStudentExistsException extends RuntimeException{

    public CourseStudentExistsException(){
    }

    public CourseStudentExistsException(String message){
        super(message);
    }

}
