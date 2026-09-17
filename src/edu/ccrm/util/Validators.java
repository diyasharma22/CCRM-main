package edu.ccrm.util;

import java.util.regex.Pattern;

/**
 * Validation utility methods.
 */
public class Validators {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Print platform summary (Java SE vs ME vs EE brief note).
     */
    public static void printJavaPlatformSummary() {
        System.out.println("\nJava Platform Summary:");
        System.out.println("Java ME: Micro Edition - for embedded/mobile devices.");
        System.out.println("Java SE: Standard Edition - for desktop and server applications.");
        // CCRM/src/edu/ccrm/util/Validators.java
        System.out.println("Java EE: Enterprise Edition - adds features for large-scale enterprise-applications.\\n");
    }
}
