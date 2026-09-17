package edu.ccrm.util;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;

import java.util.Comparator;

/**
 * Comparator lambdas for sorting.
 */
public class Comparators {
    public static final Comparator<Student> BY_FULLNAME =
        Comparator.comparing(Student::getFullName);

    public static final Comparator<Course> BY_CODE =
        Comparator.comparing(Course::getCode);
}
