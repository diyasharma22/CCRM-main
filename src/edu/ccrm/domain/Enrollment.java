// CCRM/src/edu/ccrm/domain/Enrollment.java
package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Represents the enrollment of a Student in a Course.
 * This class links a student to a course and holds enrollment-specific
 * data like the grade and enrollment date.
 */
public class Enrollment {
    private Student student;
    private Course course;
    private Grade grade;
    private LocalDate enrollmentDate;

    /**
     * Creates a new enrollment record.
     * The enrollment date is automatically set to the current date,
     * and the initial grade is set to NOT_GRADED.
     *
     * @param student The student being enrolled.
     * @param course The course the student is enrolling in.
     */
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.grade = Grade.NOT_GRADED;
    }

    // --- Getters and Setters ---
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    @Override
    public String toString() {
        return "Enrollment[Student=" + student.getFullName() + ", Course=" + course.getCode() +
                ", Grade=" + grade + ", Date=" + enrollmentDate + "]";
    }
}