// CCRM/src/edu/ccrm/domain/Grade.java
package edu.ccrm.domain;

/**
 * Enumeration representing the possible grades a student can receive in a course.
 * Each grade is associated with a numeric grade point for GPA calculation.
 */
public enum Grade {
    S(10), A(9), B(8), C(7), D(6), E(5), F(0), NOT_GRADED(-1);

    private final int gradePoint;

    Grade(int gradePoint) {
        this.gradePoint = gradePoint;
    }

    /**
     * Gets the numeric grade point associated with the grade.
     * @return The integer value of the grade point.
     */
    public int getGradePoint() {
        return gradePoint;
    }

    @Override
    public String toString() {
        return this.name();
    }
}