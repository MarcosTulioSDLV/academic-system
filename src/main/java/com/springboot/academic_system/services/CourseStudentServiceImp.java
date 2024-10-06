package com.springboot.academic_system.services;

import com.springboot.academic_system.dtos.*;
import com.springboot.academic_system.exceptions.CourseStudentExistsException;
import com.springboot.academic_system.exceptions.CourseStudentNotFoundException;
import com.springboot.academic_system.mappers.CourseStudentMapper;
import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.models.CourseStudent;
import com.springboot.academic_system.models.CourseStudentId;
import com.springboot.academic_system.models.Student;
import com.springboot.academic_system.repositories.CourseStudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CourseStudentServiceImp implements CourseStudentService{

    private final CourseStudentRepository courseStudentRepository;

    private final StudentService studentService;

    private final CourseService courseService;

    private final CourseStudentMapper courseStudentMapper;

    @Autowired
    public CourseStudentServiceImp(CourseStudentRepository courseStudentRepository, StudentService studentService, CourseService courseService, CourseStudentMapper courseStudentMapper) {
        this.courseStudentRepository = courseStudentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
        this.courseStudentMapper = courseStudentMapper;
    }


    @Override
    public CourseStudentResponseDto getCourseStudentById(CourseStudentRequestDto courseStudentRequestDto) {
        Long courseId= courseStudentRequestDto.getCourseId();
        Long studentId= courseStudentRequestDto.getStudentId();
        CourseStudent courseStudent= findCourseStudentById(courseId,studentId);
        return courseStudentMapper.toCourseStudentResponseDto(courseStudent);
    }

    public CourseStudent findCourseStudentById(Long courseId,Long studentId){
        CourseStudentId courseStudentId= new CourseStudentId(courseId,studentId);
        return courseStudentRepository.findById(courseStudentId)
                .orElseThrow(()->new CourseStudentNotFoundException(String.format("Course x Student: (Course id: %d, Student id: %d) not found!",courseId,studentId)));
        //METHOD 2 (by using a custom findByCourseAndStudent, after recovering course and student)
        /*
        Course course= courseService.findCourseById(courseId);
        Student student= studentService.findStudentById(studentId);
        return courseStudentRepository.findByCourseAndStudent(course,student)
                .orElseThrow(()->new CourseStudentNotFoundException(String.format("Course id: %d, Student id: %d not found!",courseId,studentId)));
        */
    }

    @Override
    public List<Course> getCoursesByStudentId(Long studentId) {
        Student student= studentService.findStudentById(studentId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByStudent(student);

        return  courseStudentList.stream()
                .map(CourseStudent::getCourse)
                .collect(toList());
    }

    @Override
    public List<CourseStudentSummaryResponseDto> getCourseStudentSummaryByStudentId(Long studentId) {
        Student student= studentService.findStudentById(studentId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByStudent(student);

        return courseStudentList.stream()
                .map(courseStudentMapper::toCourseStudentSummaryResponseDto)
                .collect(toList());
    }

    @Override
    public List<Student> getStudentsByCourseId(Long courseId) {
        Course course= courseService.findCourseById(courseId);
        List<CourseStudent> courseStudentList= courseStudentRepository.findByCourse(course);

        return courseStudentList.stream()
                .map(CourseStudent::getStudent)
                .collect(toList());
    }


    @Override
    @Transactional
    public List<CourseStudentResponseDto> addCourseStudent(AddCourseStudentRequestDto addCourseStudentRequestDto) {
        Long studentId= addCourseStudentRequestDto.getStudentId();
        List<Long> courseIds= addCourseStudentRequestDto.getCourseIds();
        List<CourseStudentResponseDto> courseStudentResponseDtoList= courseIds.stream()
                .map(courseId->addCourseStudent(studentId,courseId))
                .map(courseStudentMapper::toCourseStudentResponseDto)
                .toList();
        //Note: For returning a Page<>.
        //Page<CourseStudentResponseDto> CourseStudentResponseDtoPage= new PageImpl<>(courseStudentResponseDtoList,pageable,courseStudentResponseDtoList.size());
        return courseStudentResponseDtoList;
    }

    private CourseStudent addCourseStudent(Long studentId,Long courseId){
        Student student= studentService.findStudentById(studentId);
        Course course= courseService.findCourseById(courseId);

        validateUniqueId(studentId,courseId);

        course.enrollStudent();

        CourseStudent courseStudent= new CourseStudent(course,student,0D);
        //CourseStudent courseStudent= new CourseStudent(new CourseStudentId(),course,student,0D);
        return courseStudentRepository.save(courseStudent);
    }

    private void validateUniqueId(Long studentId, Long courseId) {
        CourseStudentId courseStudentId= new CourseStudentId(courseId, studentId);
        if(courseStudentRepository.existsById(courseStudentId)){
            String exceptionMessage= String.format("Course x Student: (Course id: %d, Student id: %d) already exist!", courseId, studentId);
            throw new CourseStudentExistsException(exceptionMessage);
        }
    }


    @Override
    @Transactional
    public CourseStudentResponseDto setCourseStudentScore(CourseStudentScoreRequestDto courseStudentScoreRequestDto) {
        Long courseId= courseStudentScoreRequestDto.getCourseId();
        Long studentId= courseStudentScoreRequestDto.getStudentId();
        Double score= courseStudentScoreRequestDto.getScore();

        CourseStudent courseStudent= findCourseStudentById(courseId,studentId);
        courseStudent.setScore(score);

        return courseStudentMapper.toCourseStudentResponseDto(courseStudent);
    }

    @Override
    @Transactional
    public void removeCourseStudent(CourseStudentRequestDto courseStudentRequestDto) {
        Long courseId= courseStudentRequestDto.getCourseId();
        Long studentId= courseStudentRequestDto.getStudentId();
        CourseStudent courseStudent= findCourseStudentById(courseId,studentId);

        courseStudent.getCourse().unenrollStudent();
        courseStudentRepository.delete(courseStudent);
    }

}
