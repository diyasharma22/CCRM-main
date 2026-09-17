package edu.ccrm.exceptions;

/**
 * Exception thrown when a student tries to enroll in courses
 * exceeding the max allowable credits per semester.
 */
public class MaxCreditLimitExceededException extends Exception {
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }
}
