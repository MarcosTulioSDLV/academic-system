# Academic System Rest API
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)  ![H2 Database](https://img.shields.io/badge/H2%20Database-018bff?style=for-the-badge&logoColor=white)

I developed a Rest API to manage courses and their students, in an academic system, built by using **Spring Boot and Java**, providing CRUD (Create, Read, Update, Delete) operations. This API allows to store course information, such as: course code, name, description, credits, and max students (the maximum number of students allowed in the course). Additionally, it supports the storage of student information, which includes: document, first name, last name, gender and birth date.

## Requirements

Below are some business rules that were important for the functioning:

• The course code is unique in the system, therefore, only one registration with the same code course is allowed. Similarly, the student document field is also unique in the system. 

• All fields for both course and student are mandatory and must be filled out.

• A course can have many students. Similarly, a student can be enrolled in many courses. 

• In order to enroll a student in a course, the system must check if the current students are less than the maximum students allowed. If it does, the student can be enrolled in the course. Otherwise, the system should inform that there are no available spots in the course (the course is full).

• A student has a score for each enrolled course. The score is added after the student is enrolled in a course (it is initially set to a default value of 0 and must be updated later).

• Allow to remove a student from the system. 

• After a student is removed from the system, all the related courses must be updated to indicate that an additional spot is now available (update the number of current students for those courses).

• Retrieve a list of all students enrolled in a particular course.

• Retrieve a list of all courses and their associated scores for a particular student.

• Allow to remove (or unrolled) a student from a particular course.

• After a student is removed from a particular course, the related course must be updated to indicate that an additional spot is now available.

## Database Config
For test this API, an external Database is not necessary because an embedded Database (H2 Database) was used with the following configuration properties:

- Name: academic_system_db
- Username: sa
- Password:

## Development Tools
This Rest API was built with:

- Spring Boot version: 3.3.4
- Java version: 17

## System Class Diagram

![AcademicSystemClassDiagram](https://github.com/user-attachments/assets/1c685403-226e-4888-bbe0-c08eb7b3bbb9)

