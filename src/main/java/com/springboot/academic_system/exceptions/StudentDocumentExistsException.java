package com.springboot.academic_system.exceptions;

public class StudentDocumentExistsException extends RuntimeException{

    public StudentDocumentExistsException(){
    }

    public StudentDocumentExistsException(String message){
        super(message);
    }

}
