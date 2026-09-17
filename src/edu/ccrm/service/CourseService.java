// CCRM/src/edu/ccrm/service/CourseService.java
package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages all business logic related to courses, acting as an
 * in-memory repository for course data.
 */
public class CourseService {
    private List<Course> courses = new ArrayList<>();

    /**
     * Adds a new course to the system.
     * @param course The Course object to add.
     * @throws IllegalArgumentException if a course with the same code already exists.
     */
    public void addCourse(Course course) {
        if (findCourseByCode(course.getCode()).isPresent()) {
            throw new IllegalArgumentException("Course with this code already exists.");
        }
        courses.add(course);
    }

    /**
     * Retrieves a list of all courses in the system.
     * @return A new ArrayList containing all courses to prevent modification of the original list.
     */
    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }

    /**
     * Finds a course by its unique code.
     * @param code The course code to search for.
     * @return An Optional containing the course if found, or an empty Optional otherwise.
     */
    public Optional<Course> findCourseByCode(String code) {
        return courses.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    /**
     * Updates the instructor for a given course.
     * This implementation creates a new Course object to maintain immutability.
     * @param code The code of the course to update.
     * @param newInstructor The name of the new instructor.
     * @throws IllegalArgumentException if the course is not found.
     */
    public void updateCourseInstructor(String code, String newInstructor) {
        Optional<Course> courseOpt = findCourseByCode(code);
        if (courseOpt.isPresent()) {
            Course oldCourse = courseOpt.get();
            Course updatedCourse = new Course.Builder()
                .setCode(oldCourse.getCode())
                .setTitle(oldCourse.getTitle())
                .setCredits(oldCourse.getCredits())
                .setInstructor(newInstructor)
                .setSemester(oldCourse.getSemester())
                .setDepartment(oldCourse.getDepartment())
                .build();
            courses.remove(oldCourse);
            courses.add(updatedCourse);
        } else {
            throw new IllegalArgumentException("Course not found");
        }
    }

    /**
     * Filters and returns courses taught by a specific instructor.
     * @param instructor The name of the instructor.
     * @return A list of courses matching the instructor's name (case-insensitive).
     */
    public List<Course> filterCoursesByInstructor(String instructor) {
        return courses.stream()
                .filter(c -> c.getInstructor().equalsIgnoreCase(instructor))
                .collect(Collectors.toList());
    }

    /**
     * Filters and returns courses belonging to a specific department.
     * @param department The name of the department.
     * @return A list of courses in the specified department (case-insensitive).
     */
    public List<Course> filterCoursesByDepartment(String department) {
        return courses.stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    /**
     * Filters and returns courses offered in a specific semester.
     * @param semester The Semester enum value.
     * @return A list of courses offered in that semester.
     */
    public List<Course> filterCoursesBySemester(Semester semester) {
        return courses.stream()
                .filter(c -> c.getSemester() == semester)
                .collect(Collectors.toList());
    }

    /**
     * Finds courses that have no students enrolled in them.
     * @param allEnrollments A list of all enrollment records from EnrollmentService.
     * @return A list of courses with zero enrollments.
     */
    public List<Course> findCoursesWithNoEnrollments(List<Enrollment> allEnrollments) {
        // Create a set of course codes that have at least one enrollment
        Set<String> enrolledCourseCodes = allEnrollments.stream()
                .map(enrollment -> enrollment.getCourse().getCode())
                .collect(Collectors.toSet());

        // Filter the main course list to find courses whose codes are NOT in the set
        return courses.stream()
                .filter(course -> !enrolledCourseCodes.contains(course.getCode()))
                .collect(Collectors.toList());
    }
}
