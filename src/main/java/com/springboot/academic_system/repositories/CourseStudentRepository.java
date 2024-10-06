package com.springboot.academic_system.repositories;

import com.springboot.academic_system.models.Course;
import com.springboot.academic_system.models.CourseStudent;
import com.springboot.academic_system.models.CourseStudentId;
import com.springboot.academic_system.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent,CourseStudentId> {

    List<CourseStudent> findByStudent(Student student);

    List<CourseStudent> findByCourse(Course course);

    Optional<CourseStudent> findByCourseAndStudent(Course course,Student student);

}
