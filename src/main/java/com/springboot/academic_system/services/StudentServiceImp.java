package com.springboot.academic_system.services;

import com.springboot.academic_system.dtos.StudentRequestDto;
import com.springboot.academic_system.dtos.StudentResponseDto;
import com.springboot.academic_system.exceptions.StudentDocumentExistsException;
import com.springboot.academic_system.exceptions.StudentNotFoundException;
import com.springboot.academic_system.mappers.StudentMapper;
import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.models.CourseStudent;
import com.springboot.academic_system.models.Student;
import com.springboot.academic_system.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImp implements StudentService{

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImp(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toStudentResponseDto);
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student= findStudentById(id);
        return studentMapper.toStudentResponseDto(student);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->new StudentNotFoundException("Student with id:" + id + " not found!"));
    }

    @Override
    @Transactional
    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {
        Student student= studentMapper.toStudent(studentRequestDto);
        validateUniqueFields(student);
        return studentMapper.toStudentResponseDto(studentRepository.save(student));
    }

    private void validateUniqueFields(Student student) {
        if(studentRepository.existsByDocument(student.getDocument())){
            throw new StudentDocumentExistsException("Student document: "+student.getDocument()+" already exists!");
        }
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto,Long id) {
        Student student= studentMapper.toStudent(studentRequestDto);
        student.setId(id);

        Student recoveredStudent= findStudentById(id);

        validateUpdateFieldConflicts(student,recoveredStudent);

        BeanUtils.copyProperties(student,recoveredStudent,"courseStudentList");//Note:Ignore relationships properties!
        return studentMapper.toStudentResponseDto(recoveredStudent);
    }

    private void validateUpdateFieldConflicts(Student student,Student recoveredStudent) {
        if(documentExistsAndBelongsToAnotherStudent(student.getDocument(),recoveredStudent)){
            throw new StudentDocumentExistsException("Student document: "+ student.getDocument()+" already exists!");
        }
    }

    private boolean documentExistsAndBelongsToAnotherStudent(String document,Student recoveredStudent) {
        return studentRepository.existsByDocument(document) && !document.equals(recoveredStudent.getDocument());
    }


    @Override
    @Transactional
    public void removeStudent(Long id) {
        Student student= findStudentById(id);
        unenrollFromRelatedCourses(student);
        studentRepository.delete(student);
    }


    private static void unenrollFromRelatedCourses(Student student) {
        student.getCourseStudentList().stream()
                .map(CourseStudent::getCourse)
                .forEach(Course::unenrollStudent);
    }

}
