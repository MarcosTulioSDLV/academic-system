package com.springboot.academic_system.exceptions;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(){
    }

    public StudentNotFoundException(String message){
        super(message);
    }
}
