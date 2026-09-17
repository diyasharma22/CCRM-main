// CCRM/src/edu/ccrm/domain/Person.java
package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * An abstract base class for individuals in the system, such as Students and Instructors.
 * It contains common properties like full name, email, and joined date.
 */
public abstract class Person {
    protected String fullName;
    protected String email;
    protected LocalDate joinedDate;

    /**
     * Constructor for the Person class.
     * @param fullName The full name of the person.
     * @param email The email address of the person.
     * @param joinedDate The date the person joined the institution.
     */
    public Person(String fullName, String email, LocalDate joinedDate) {
        this.fullName = fullName;
        this.email = email;
        this.joinedDate = joinedDate;
    }

    // --- Getters and Setters ---
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public LocalDate getJoinedDate() { return joinedDate; }
    public void setEmail(String email) { this.email = email; }

    /**
     * An abstract method to be implemented by subclasses.
     * It should return a formatted string with the person's profile information.
     * @return A string representing the person's profile.
     */
    public abstract String getProfileInfo();

    @Override
    public String toString() {
        return getProfileInfo();
    }
}