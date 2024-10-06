package com.springboot.academic_system.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter @Setter @ToString @EqualsAndHashCode
@Embeddable
public class CourseStudentId implements Serializable {

    private static final long serialVersionUID=1;

    private Long courseId;

    private Long studentId;

    public CourseStudentId(){
    }

}
