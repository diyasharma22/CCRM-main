// CCRM/src/edu/ccrm/domain/Course.java
package edu.ccrm.domain;

import java.util.Objects;

/**
 * Represents a course offered by the institution.
 * This class is immutable; instances are created using the Builder pattern.
 */
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final String instructor;
    private final Semester semester;
    private final String department;

    /**
     * Private constructor to be used exclusively by the Builder class.
     * @param builder The builder instance containing the course data.
     */
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
    }

    // --- Getters ---
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return code + " - " + title + " (" + credits + " credits), Instructor: " + instructor +
                ", Semester: " + semester + ", Department: " + department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /**
     * Builder class for creating immutable instances of Course.
     * This pattern provides a clear and readable way to construct complex objects.
     */
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCredits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder setInstructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder setSemester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }

        /**
         * Constructs and returns an immutable Course object.
         * @return A new instance of Course.
         */
        public Course build() {
            return new Course(this);
        }
    }
}