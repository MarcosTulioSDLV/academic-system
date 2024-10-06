package com.springboot.academic_system.services;

import com.springboot.academic_system.dtos.CourseRequestDto;
import com.springboot.academic_system.dtos.CourseResponseDto;
import com.springboot.academic_system.exceptions.CourseCodeExistsException;
import com.springboot.academic_system.exceptions.CourseNotFoundException;
import com.springboot.academic_system.exceptions.CourseMaxStudentsLessThanCurrentStudents;
import com.springboot.academic_system.mappers.CourseMapper;
import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.repositories.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImp implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;


    @Autowired
    public CourseServiceImp(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public Page<CourseResponseDto> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toCourseResponseDto);
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course= findCourseById(id);
        return courseMapper.toCourseResponseDto(course);
    }

    public Course findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(() ->new CourseNotFoundException("Course with id: " + id + " not found!"));
    }

    @Override
    @Transactional
    public CourseResponseDto addCourse(CourseRequestDto courseRequestDto) {
        Course course= courseMapper.toCourse(courseRequestDto);
        course.setCurrentStudents(0);
        validateUniqueFields(course);
        return courseMapper.toCourseResponseDto(courseRepository.save(course));
    }

    private void validateUniqueFields(Course course) {
        if(courseRepository.existsByCourseCodeIgnoreCase(course.getCourseCode())){
            throw new CourseCodeExistsException("Course Code: "+ course.getCourseCode()+" already exists!");
        }
    }

    @Override
    @Transactional
    public CourseResponseDto updateCourse(CourseRequestDto courseRequestDto, Long id) {
        Course course= courseMapper.toCourse(courseRequestDto);
        course.setId(id);

        Course recoveredCourse= findCourseById(id);

        validateFieldsUpdateConflict(course,recoveredCourse);

        BeanUtils.copyProperties(course,recoveredCourse,"courseStudentList","currentStudents");//Note:Ignore relationships properties!
        return courseMapper.toCourseResponseDto(recoveredCourse);
    }

    private void validateFieldsUpdateConflict(Course course,Course recoveredCourse) {
        if(courseCodeExistsAndBelongsToAnotherInstance(course.getCourseCode(),recoveredCourse)){
            throw new CourseCodeExistsException("Course Code: "+ course.getCourseCode()+" already exists!");
        }
        if(course.getMaxStudents()<recoveredCourse.getCurrentStudents()){
            throw new CourseMaxStudentsLessThanCurrentStudents("Max Students:"+course.getMaxStudents()+", is less than Current Students: "+recoveredCourse.getCurrentStudents());
        }
    }

    private boolean courseCodeExistsAndBelongsToAnotherInstance(String courseCode,Course recoveredCourse) {
        return courseRepository.existsByCourseCodeIgnoreCase(courseCode) && !courseCode.equalsIgnoreCase(recoveredCourse.getCourseCode());
    }

    @Override
    @Transactional
    public void removeCourse(Long id) {
        Course course= findCourseById(id);
        courseRepository.delete(course);
    }

}
