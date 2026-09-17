// CCRM/src/edu/ccrm/domain/Student.java
package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a student in the system, extending the base Person class.
 * The registration number (regNo) is the unique identifier for a student.
 */
public class Student extends Person {
    private String regNo;
    private Status status;
    private List<String> enrolledCourses;

    /**
     * Enumeration for the student's current status (e.g., ACTIVE or DEACTIVATED).
     */
    public enum Status {
        ACTIVE, DEACTIVATED
    }

    /**
     * Constructs a new Student object.
     * The student's status is automatically set to ACTIVE and an empty list of
     * enrolled courses is initialized.
     *
     * @param regNo    The unique registration number.
     * @param fullName The student's full name.
     * @param email    The student's email address.
     */
    public Student(String regNo, String fullName, String email) {
        super(fullName, email, LocalDate.now());
        if (regNo == null || regNo.isEmpty()) throw new IllegalArgumentException("Registration number cannot be empty");
        this.regNo = regNo;
        this.status = Status.ACTIVE;
        this.enrolledCourses = new ArrayList<>();
    }

    // --- Getters and Setters ---
    public String getRegNo() { return regNo; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }

    /**
     * Enrolls the student in a course by adding the course code to their list.
     * @param courseCode The code of the course to enroll in.
     */
    public void enrollCourse(String courseCode) {
        if (!enrolledCourses.contains(courseCode)) {
            enrolledCourses.add(courseCode);
        }
    }

    /**
     * Unenrolls the student from a course by removing the course code.
     * @param courseCode The code of the course to unenroll from.
     */
    public void unenrollCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
    }

    @Override
    public String getProfileInfo() {
        return "Student[RegNo=" + regNo + ", Name=" + fullName + ", Email=" + email + ", Status=" + status + "]";
    }

    @Override
    public String toString() {
        return getProfileInfo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return regNo.equals(student.regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }
}