// CCRM/src/edu/ccrm/service/TranscriptService.java
package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import java.util.List;

/**
 * Provides services related to generating student transcripts and calculating GPA.
 */
public class TranscriptService {

    /**
     * Computes the Grade Point Average (GPA) from a list of enrollments.
     * Grades of F and NOT_GRADED are excluded from the calculation.
     *
     * @param enrollments The list of a student's enrollments.
     * @return The calculated GPA as a double. Returns 0.0 if there are no graded courses.
     */
    public double computeGPA(List<Enrollment> enrollments) {
        if (enrollments == null || enrollments.isEmpty()) {
            return 0.0;
        }

        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment e : enrollments) {
            Grade grade = e.getGrade();
            // Only include courses that have been graded and passed in the GPA calculation.
            if (grade != Grade.NOT_GRADED && grade != Grade.F) {
                int points = grade.getGradePoint();
                int credits = e.getCourse().getCredits();
                totalPoints += points * credits;
                totalCredits += credits;
            }
        }
        // Avoid division by zero if a student has no credits from passed courses.
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    /**
     * Generates a formatted string representing a student's transcript.
     *
     * @param studentName The full name of the student.
     * @param enrollments A list of the student's enrollments.
     * @return A formatted string containing the transcript details and GPA.
     */
    public String generateTranscriptView(String studentName, List<Enrollment> enrollments) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Transcript for: ").append(studentName).append(" ---\n");
        sb.append("--------------------------------------------------\n");
        for (Enrollment e : enrollments) {
            sb.append(String.format("%-10s | %-30s | Grade: %s\n",
                    e.getCourse().getCode(),
                    e.getCourse().getTitle(),
                    e.getGrade()));
        }
        double gpa = computeGPA(enrollments);
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("Cumulative GPA: %.2f\n", gpa));
        sb.append("--------------------------------------------------\n");
        return sb.toString();
    }
}