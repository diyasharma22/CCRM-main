package edu.ccrm.exceptions;

/**
 * Exception thrown when a student tries to enroll in a course
 * they are already enrolled in.
 */
public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}
