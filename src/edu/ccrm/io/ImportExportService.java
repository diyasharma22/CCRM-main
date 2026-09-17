// CCRM/src/edu/ccrm/io/ImportExportService.java
package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class ImportExportService {
    private static final Path STUDENT_CSV = Paths.get("data/students.csv");
    private static final Path COURSE_CSV = Paths.get("data/courses.csv");
    private static final Path ENROLLMENT_CSV = Paths.get("data/enrollments.csv");

    public void importStudents(StudentService studentService) throws IOException {
        List<String> lines = Files.readAllLines(STUDENT_CSV);
        if (lines.size() > 1) {
            lines = lines.subList(1, lines.size());
        } else {
            return;
        }
        for (String line : lines) {
            String[] tokens = line.split(",");
            if (tokens.length >= 3) {
                String regNo = tokens[0].trim();
                String fullName = tokens[1].trim();
                String email = tokens[2].trim();
                try {
                    studentService.addStudent(regNo, fullName, email);
                } catch (IllegalArgumentException ignore) {
                    // skip duplicates
                }
            }
        }
    }

    public void exportStudents(List<Student> students) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("regNo,fullName,email\n");
        for (Student s : students) {
            sb.append(s.getRegNo()).append(",")
              .append(s.getFullName()).append(",")
              .append(s.getEmail()).append("\n");
        }
        Files.write(STUDENT_CSV, sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void importCourses(CourseService courseService) throws IOException {
        List<String> lines = Files.readAllLines(COURSE_CSV);
        if (lines.size() > 1) {
            lines = lines.subList(1, lines.size());
        } else {
            return;
        }
        for (String line : lines) {
            String[] tokens = line.split(",");
            if (tokens.length >= 6) {
                Course course = new Course.Builder()
                        .setCode(tokens[0].trim())
                        .setTitle(tokens[1].trim())
                        .setCredits(Integer.parseInt(tokens[2].trim()))
                        .setInstructor(tokens[3].trim())
                        .setSemester(Semester.valueOf(tokens[4].trim().toUpperCase()))
                        .setDepartment(tokens[5].trim())
                        .build();
                try {
                    courseService.addCourse(course);
                } catch (IllegalArgumentException ignore) {
                    // skip duplicates
                }
            }
        }
    }

    public void exportCourses(List<Course> courses) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("code,title,credits,instructor,semester,department\n");
        for (Course c : courses) {
            sb.append(c.getCode()).append(",")
              .append(c.getTitle()).append(",")
              .append(c.getCredits()).append(",")
              .append(c.getInstructor()).append(",")
              .append(c.getSemester()).append(",")
              .append(c.getDepartment()).append("\n");
        }
        Files.write(COURSE_CSV, sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void importEnrollments(EnrollmentService enrollmentService) throws IOException {
        List<String> lines = Files.readAllLines(ENROLLMENT_CSV);
        if (lines.size() > 1) {
            lines = lines.subList(1, lines.size());
        } else {
            return;
        }

        for (String line : lines) {
            String[] tokens = line.split(",");
            if (tokens.length >= 2) {
                String regNo = tokens[0].trim();
                String courseCode = tokens[1].trim();
                try {
                    enrollmentService.enrollStudent(regNo, courseCode);
                } catch (MaxCreditLimitExceededException | DuplicateEnrollmentException e) {
                    System.out.println("Could not enroll student " + regNo + " in course " + courseCode + ": " + e.getMessage());
                }
            }
        }
    }
}