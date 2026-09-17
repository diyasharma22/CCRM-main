// CCRM/src/edu/ccrm/domain/Semester.java
package edu.ccrm.domain;

/**
 * Enumeration for the academic semesters.
 */
public enum Semester {
    SPRING, SUMMER, FALL;

    @Override
    public String toString() {
        return this.name();
    }
}