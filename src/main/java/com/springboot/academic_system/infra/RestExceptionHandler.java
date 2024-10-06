package com.springboot.academic_system.infra;

import com.springboot.academic_system.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    //For Course class
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException e){
        return handleCustomExceptions(e,HttpStatus.NOT_FOUND);
        /* //Old method, without refactoring
        HttpStatus httpStatus= HttpStatus.NOT_FOUND;
        Map<String,Object> responseMessage= new LinkedHashMap<>();
        responseMessage.put("message",e.getMessage());
        responseMessage.put("status",httpStatus.value());//httpStatus.value()
        return new ResponseEntity<>(responseMessage,httpStatus);
        */
    }

    @ExceptionHandler(CourseCodeExistsException.class)
    public ResponseEntity<Object> handleCourseCodeExistsException(CourseCodeExistsException e){
        return handleCustomExceptions(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseMaxStudentsLessThanCurrentStudents.class)
    public ResponseEntity<Object> handleMaxStudentsLessThanCurrentStudents(CourseMaxStudentsLessThanCurrentStudents e){
        return handleCustomExceptions(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For Student Class

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Object> handleStudentNotFoundException(StudentNotFoundException e){
        return handleCustomExceptions(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentDocumentExistsException.class)
    public ResponseEntity<Object> handleStudentDocumentExistsException(StudentDocumentExistsException e){
        return handleCustomExceptions(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For CourseStudent class

    @ExceptionHandler(CourseStudentNotFoundException.class)
    public ResponseEntity<Object> handleCourseStudentNotFoundException(CourseStudentNotFoundException e){
        return handleCustomExceptions(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseStudentExistsException.class)
    public ResponseEntity<Object> handleCourseStudentExistsException(CourseStudentExistsException e){
        return handleCustomExceptions(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CourseMaxStudentsFullException.class)
    public ResponseEntity<Object> handleCourseMaxStudentsFullException(CourseMaxStudentsFullException e){
        return handleCustomExceptions(e,HttpStatus.BAD_REQUEST);
    }
    //-----

    private ResponseEntity<Object> handleCustomExceptions(RuntimeException e,HttpStatus status) {
        Map<String,Object> responseMessage = createResponseMessage(e.getMessage(),status);
        return new ResponseEntity<>(responseMessage,status);
    }

    private Map<String,Object> createResponseMessage(String message,HttpStatus status) {
        Map<String,Object> responseMessage = new LinkedHashMap<>();
        responseMessage.put("message",message);
        responseMessage.put("status",status.value());
        return responseMessage;
    }

}
