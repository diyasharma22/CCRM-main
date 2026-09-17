// CCRM/src/edu/ccrm/domain/Instructor.java
package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Represents an instructor, extending the base Person class.
 * Includes instructor-specific details like an instructor ID and department.
 */
public class Instructor extends Person {
    private String instructorId;
    private String department;

    /**
     * Constructs a new Instructor.
     *
     * @param instructorId The unique ID for the instructor.
     * @param fullName     The full name of the instructor.
     * @param email        The instructor's email address.
     * @param department   The department the instructor belongs to.
     */
    public Instructor(String instructorId, String fullName, String email, String department) {
        super(fullName, email, LocalDate.now());
        if (instructorId == null || instructorId.isEmpty()) throw new IllegalArgumentException("Instructor ID cannot be empty");
        this.instructorId = instructorId;
        this.department = department;
    }

    public String getInstructorId() { return instructorId; }
    public String getDepartment() { return department; }
    public void setDepartment(String dept) { this.department = dept; }

    /**
     * Generates a profile summary for the instructor.
     * @return A string containing the instructor's key details.
     */
    @Override
    public String getProfileInfo() {
        return "Instructor[ID=" + instructorId + ", Name=" + fullName + ", Email=" + email + ", Department=" + department + "]";
    }

    @Override
    public String toString() {
        return getProfileInfo();
    }
}