package com.springboot.academic_system.repositories;

import com.springboot.academic_system.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    boolean existsByCourseCodeIgnoreCase(String courseCode);

}
