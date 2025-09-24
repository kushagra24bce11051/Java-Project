package edu.ccrm.exception;

/**
 * Custom exception for duplicate enrollment
 * Demonstrates custom exception creation
 */
public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String message) {
        super(message);
    }

    public DuplicateEnrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}



