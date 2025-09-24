package edu.ccrm.exception;

/**
 * Custom exception for credit limit exceeded
 * Demonstrates custom exception creation
 */
public class MaxCreditLimitExceededException extends Exception {
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }

    public MaxCreditLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}



