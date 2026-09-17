// CCRM/src/edu/ccrm/service/StudentService.java
package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages all operations related to students, including adding,
 * finding, and updating student records. This class acts as an
 * in-memory database for student objects.
 */
public class StudentService {
    private List<Student> students = new ArrayList<>();

    /**
     * Adds a new student to the system.
     * Prevents adding a student if one with the same registration number already exists.
     *
     * @param regNo    The unique registration number for the student.
     * @param fullName The full name of the student.
     * @param email    The student's email address.
     * @throws IllegalArgumentException if a student with the same regNo already exists.
     */
    public void addStudent(String regNo, String fullName, String email) {
        if (findStudentByRegNo(regNo).isPresent()) {
            throw new IllegalArgumentException("Student with this registration number already exists.");
        }
        // Create a new Student instance and add it to our list.
        Student student = new Student(regNo, fullName, email);
        students.add(student);
    }

    /**
     * Retrieves a list of all students currently in the system.
     *
     * @return A new ArrayList containing all students, to prevent external modification of the master list.
     */
    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Finds a single student by their unique registration number.
     *
     * @param regNo The registration number to search for.
     * @return An Optional containing the found Student, or an empty Optional if no student is found.
     */
    public Optional<Student> findStudentByRegNo(String regNo) {
        return students.stream()
                .filter(s -> s.getRegNo().equals(regNo))
                .findFirst();
    }

    /**
     * Updates the email address of an existing student.
     *
     * @param regNo    The registration number of the student to update.
     * @param newEmail The new email address to set.
     * @throws IllegalArgumentException if no student is found with the given registration number.
     */
    public void updateStudentEmail(String regNo, String newEmail) {
        findStudentByRegNo(regNo).ifPresentOrElse(
                s -> s.setEmail(newEmail),
                () -> { throw new IllegalArgumentException("Student not found"); }
        );
    }

    /**
     * Sets a student's status to DEACTIVATED.
     *
     * @param regNo The registration number of the student to deactivate.
     * @throws IllegalArgumentException if no student is found with the given registration number.
     */
    public void deactivateStudent(String regNo) {
        findStudentByRegNo(regNo).ifPresentOrElse(
                s -> s.setStatus(Student.Status.DEACTIVATED),
                () -> { throw new IllegalArgumentException("Student not found"); }
        );
    }
}