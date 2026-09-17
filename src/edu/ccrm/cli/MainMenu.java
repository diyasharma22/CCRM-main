// CCRM/src/edu/ccrm/cli/MainMenu.java
package edu.ccrm.cli;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Semester;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.io.*;
import edu.ccrm.service.*;
import edu.ccrm.util.Validators;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the Command Line Interface (CLI) of the CCRM application.
 * This class handles all user interactions, menu displays, and calls to the service layer.
 */
public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
    private TranscriptService transcriptService = new TranscriptService();
    private ImportExportService importExportService = new ImportExportService();
    private BackupService backupService = new BackupService();

    /**
     * Starts the main application loop.
     */
    public void run() {
        // Initial data loading from CSV files on startup.
        try {
            importExportService.importStudents(studentService);
            importExportService.importCourses(courseService);
            importExportService.importEnrollments(enrollmentService);
            System.out.println("Initial data loaded successfully from CSV files.");
        } catch (IOException e) {
            System.out.println("Failed to load initial data: " + e.getMessage());
        }

        boolean exit = false;
        while (!exit) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": manageStudents(); break;
                case "2": manageCourses(); break;
                case "3": manageEnrollment(); break;
                case "4": recordGrades(); break;
                case "5": printTranscript(); break;
                case "6": importExportData(); break;
                case "7": backupData(); break;
                case "8": showReports(); break;
                case "9": exit = true; System.out.println("Exiting program. Goodbye!"); break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu options to the user.
     */
    private void printMenu() {
        System.out.println("\n=== Campus Course & Records Manager (CCRM) ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment");
        System.out.println("4. Record Grades");
        System.out.println("5. Print Transcript");
        System.out.println("6. Import/Export Data");
        System.out.println("7. Backup Data");
        System.out.println("8. Reports");
        System.out.println("9. Exit");
        System.out.print("Select an option from the menu: ");
    }

    /**
     * Displays the student management sub-menu and handles user input.
     */
    private void manageStudents() {
        System.out.println("\n-- Student Management --");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Update Student");
        System.out.println("4. Deactivate Student");
        System.out.println("5. Back to main menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1": addStudent(); break;
            case "2": listStudents(); break;
            case "3": updateStudent(); break;
            case "4": deactivateStudent(); break;
            case "5": return;
            default: System.out.println("Invalid option.");
        }
    }

    /**
     * Prompts for and adds a new student to the system.
     */
    private void addStudent() {
        System.out.print("Enter registration number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (!Validators.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        try {
            studentService.addStudent(regNo, fullName, email);
            System.out.println("Student added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays a formatted table of all students.
     */
    private void listStudents() {
        System.out.println("\n--- List of All Students ---");
        System.out.println(String.format("%-15s | %-20s | %-30s | %s", "Reg No", "Full Name", "Email", "Status"));
        System.out.println("--------------------------------------------------------------------------------");
        studentService.listStudents().forEach(student ->
            System.out.println(String.format("%-15s | %-20s | %-30s | %s",
                student.getRegNo(),
                student.getFullName(),
                student.getEmail(),
                student.getStatus()
            ))
        );
        System.out.println("--------------------------------------------------------------------------------");
    }

    /**
     * Prompts for student details to update their email.
     */
    private void updateStudent() {
        System.out.print("Enter student registration number to update: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();

        if (!Validators.isValidEmail(newEmail)) {
            System.out.println("Invalid email format.");
            return;
        }

        try {
            studentService.updateStudentEmail(regNo, newEmail);
            System.out.println("Student email updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts for a student's registration number to deactivate them.
     */
    private void deactivateStudent() {
        System.out.print("Enter student registration number to deactivate: ");
        String regNo = scanner.nextLine();
        try {
            studentService.deactivateStudent(regNo);
            System.out.println("Student deactivated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays the course management sub-menu.
     */
    private void manageCourses() {
        System.out.println("\n-- Course Management --");
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": addCourse(); break;
            case "2": listCourses(); break;
            case "3": return;
            default: System.out.println("Invalid option.");
        }
    }

    /**
     * Prompts for and adds a new course.
     */
    private void addCourse() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter instructor: ");
        String instructor = scanner.nextLine();
        System.out.print("Enter semester (SPRING, SUMMER, FALL): ");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter department: ");
        String department = scanner.nextLine();

        Course course = new Course.Builder()
                .setCode(code)
                .setTitle(title)
                .setCredits(credits)
                .setInstructor(instructor)
                .setSemester(semester)
                .setDepartment(department)
                .build();
        try {
            courseService.addCourse(course);
            System.out.println("Course added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Displays a formatted table of all courses.
     */
    private void listCourses() {
        System.out.println("\n--- List of All Courses ---");
        System.out.println(String.format("%-10s | %-45s | %-7s | %s", "Code", "Title", "Credits", "Department"));
        System.out.println("-----------------------------------------------------------------------------------------");
        courseService.listCourses().forEach(course ->
            System.out.println(String.format("%-10s | %-45s | %-7s | %s",
                course.getCode(),
                course.getTitle(),
                course.getCredits(),
                course.getDepartment()
            ))
        );
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /**
     * Displays the enrollment management sub-menu.
     */
    private void manageEnrollment() {
        System.out.println("\n-- Enrollment Management --");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Unenroll Student from Course");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": enrollStudent(); break;
            case "2": unenrollStudent(); break;
            case "3": return;
            default: System.out.println("Invalid option.");
        }
    }

    /**
     * Prompts for details to enroll a student in a course.
     */
    private void enrollStudent() {
        System.out.print("Enter student registration number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        try {
            enrollmentService.enrollStudent(regNo, courseCode);
            System.out.println("Student enrolled successfully.");
        } catch (MaxCreditLimitExceededException | DuplicateEnrollmentException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts for details to unenroll a student from a course.
     */
    private void unenrollStudent() {
        System.out.print("Enter student registration number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        enrollmentService.unenrollStudent(regNo, courseCode);
        System.out.println("Student unenrolled successfully.");
    }

    /**
     * Prompts for details to record a grade for a student's enrollment.
     */
    private void recordGrades() {
        System.out.print("Enter student registration number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter grade (S, A, B, C, D, E, F): ");
        try {
            Grade grade = Grade.valueOf(scanner.nextLine().toUpperCase());
            enrollmentService.recordGrade(regNo, courseCode, grade);
            System.out.println("Grade recorded successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid grade or enrollment record not found.");
        }
    }

    /**
     * Prompts for a student's registration number and prints their transcript.
     */
    private void printTranscript() {
        System.out.print("Enter student registration number: ");
        String regNo = scanner.nextLine();
        studentService.findStudentByRegNo(regNo).ifPresentOrElse(
                student -> {
                    String transcript = transcriptService.generateTranscriptView(
                            student.getFullName(),
                            enrollmentService.getEnrollmentsForStudent(regNo)
                    );
                    System.out.println(transcript);
                },
                () -> System.out.println("Student not found.")
        );
    }

    /**
     * Displays the import/export sub-menu.
     */
    private void importExportData() {
        System.out.println("\n-- Import/Export Data --");
        System.out.println("1. Import All Data");
        System.out.println("2. Export All Data");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1":
                    importExportService.importStudents(studentService);
                    importExportService.importCourses(courseService);
                    importExportService.importEnrollments(enrollmentService);
                    System.out.println("Data re-imported successfully.");
                    break;
                case "2":
                    importExportService.exportStudents(studentService.listStudents());
                    importExportService.exportCourses(courseService.listCourses());
                    System.out.println("Data exported successfully to the 'data' directory.");
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (IOException e) {
            System.out.println("Error during import/export: " + e.getMessage());
        }
    }

    /**
     * Initiates the data backup process.
     */
    private void backupData() {
        System.out.println("Backing up data...");
        try {
            backupService.backupExports();
        } catch (IOException | RuntimeException e) {
            System.out.println("AN ERROR OCCURRED DURING BACKUP:");
            e.printStackTrace(); // This provides a detailed error trace for debugging.
        }
    }

    /**
     * Displays the reports sub-menu.
     */
    private void showReports() {
        System.out.println("\n--- Reports Menu ---");
        System.out.println("1. List Courses by Department");
        System.out.println("2. List Students in a Course");
        System.out.println("3. List Courses with No Enrollments");
        System.out.println("4. Back to Main Menu");
        System.out.print("Select a report to generate: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": listCoursesByDepartment(); break;
            case "2": listStudentsInCourse(); break;
            case "3": listCoursesWithNoEnrollments(); break;
            case "4": return;
            default: System.out.println("Invalid option.");
        }
    }

    /**
     * Prompts for a department and lists all courses within it.
     */
    private void listCoursesByDepartment() {
        System.out.print("Enter department name: ");
        String department = scanner.nextLine();
        System.out.println("\n--- Courses in " + department + " ---");
        List<Course> courses = courseService.filterCoursesByDepartment(department);
        if (courses.isEmpty()) {
            System.out.println("No courses found for this department.");
        } else {
            courses.forEach(System.out::println);
        }
    }

    /**
     * Prompts for a course code and lists all students enrolled.
     */
    private void listStudentsInCourse() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.println("\n--- Students in course " + courseCode + " ---");
        List<edu.ccrm.domain.Enrollment> enrollments = enrollmentService.getEnrollmentsForCourse(courseCode);
        if (enrollments.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
        } else {
            enrollments.forEach(enrollment -> System.out.println(enrollment.getStudent()));
        }
    }
    
    /**
     * Generates and displays a report of courses with no student enrollments.
     */
    private void listCoursesWithNoEnrollments() {
        System.out.println("\n--- Courses with No Enrollments ---");
        List<Course> unEnrolledCourses = courseService.findCoursesWithNoEnrollments(enrollmentService.getAllEnrollments());

        if (unEnrolledCourses.isEmpty()) {
            System.out.println("All courses have at least one student enrolled.");
        } else {
            unEnrolledCourses.forEach(course ->
                System.out.println(String.format("%-10s | %s", course.getCode(), course.getTitle()))
            );
        }
    }
}