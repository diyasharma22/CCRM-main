// CCRM/src/edu/ccrm/service/EnrollmentService.java
package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.exceptions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all business logic for student enrollments. This includes enrolling,
 * unenrolling, and grading. It interacts with StudentService and CourseService
 * to validate data.
 */
public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();
    private StudentService studentService;
    private CourseService courseService;
    private static final int MAX_CREDITS_PER_SEMESTER = 18;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Enrolls a student in a course after performing necessary validation checks.
     * @param regNo The registration number of the student.
     * @param courseCode The code of the course.
     * @throws MaxCreditLimitExceededException if the student exceeds the semester credit limit.
     * @throws DuplicateEnrollmentException if the student is already enrolled in the course.
     */
    public void enrollStudent(String regNo, String courseCode) throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        // Find the student and course, otherwise throw an exception
        Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with registration number: " + regNo));
        Course course = courseService.findCourseByCode(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with code: " + courseCode));

        // Check for duplicate enrollment
        if (isEnrolled(student, course))
            throw new DuplicateEnrollmentException("Student already enrolled in this course.");

        // Check if adding this course exceeds the maximum credit limit for the semester
        int currentCredits = getCurrentCredits(student, course.getSemester());
        if ((currentCredits + course.getCredits()) > MAX_CREDITS_PER_SEMESTER)
            throw new MaxCreditLimitExceededException("Max credit limit exceeded for the semester.");

        // Create and record the new enrollment
        Enrollment enrollment = new Enrollment(student, course);
        enrollments.add(enrollment);
        student.enrollCourse(courseCode);
    }

    /**
     * Unenrolls a student from a course.
     * @param regNo The registration number of the student.
     * @param courseCode The code of the course.
     */
    public void unenrollStudent(String regNo, String courseCode) {
        enrollments.removeIf(enrollment ->
                enrollment.getStudent().getRegNo().equals(regNo) &&
                enrollment.getCourse().getCode().equals(courseCode));
        studentService.findStudentByRegNo(regNo).ifPresent(s -> s.unenrollCourse(courseCode));
    }

    /**
     * Records or updates a grade for a student in a specific course.
     * @param regNo The registration number of the student.
     * @param courseCode The code of the course.
     * @param grade The Grade to be assigned.
     * @throws IllegalArgumentException if the enrollment record cannot be found.
     */
    public void recordGrade(String regNo, String courseCode, Grade grade) {
        enrollments.stream()
            .filter(e -> e.getStudent().getRegNo().equals(regNo) && e.getCourse().getCode().equals(courseCode))
            .findFirst()
            .ifPresentOrElse(
                e -> e.setGrade(grade),
                () -> { throw new IllegalArgumentException("Enrollment record not found."); }
            );
    }

    /**
     * Retrieves all enrollment records for a specific student.
     * @param regNo The student's registration number.
     * @return A list of Enrollment objects.
     */
    public List<Enrollment> getEnrollmentsForStudent(String regNo) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudent().getRegNo().equals(regNo)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Retrieves all enrollment records for a specific course.
     * @param courseCode The course code.
     * @return A list of enrollments for the given course.
     */
    public List<Enrollment> getEnrollmentsForCourse(String courseCode) {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getCourse().getCode().equals(courseCode)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Returns a copy of the list of all enrollments in the system.
     * @return A new ArrayList containing all enrollments.
     */
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    /**
     * Private helper to check if a student is already enrolled in a course.
     */
    private boolean isEnrolled(Student student, Course course) {
        return enrollments.stream().anyMatch(enrollment ->
                enrollment.getStudent().equals(student) &&
                enrollment.getCourse().equals(course));
    }

    /**
     * Private helper to calculate the current total credits for a student in a given semester.
     */
    private int getCurrentCredits(Student student, Semester semester) {
        return enrollments.stream()
            .filter(e -> e.getStudent().equals(student) && e.getCourse().getSemester() == semester)
            .mapToInt(e -> e.getCourse().getCredits())
            .sum();
    }
}