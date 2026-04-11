package com.benefits.exception;

/**
 * Thrown when an attempt is made to create a profile that
 * already exists in the system for the same Employee ID.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This exception has one job — represent a "duplicate profile" error.
 *
 * GRASP - Information Expert:
 * Carries all relevant context about the duplication attempt so
 * callers can inform the user or HR system precisely.
 */
public class DuplicateProfileException extends Exception {

    // ── Error Code ───────────────────────────────────────────────────────────

    /**
     * Unique error code for this exception type.
     * Matches the exception table in the project specification.
     */
    private static final String ERROR_CODE = "DUPLICATE_PROFILE";

    // ── Constructors ─────────────────────────────────────────────────────────

    /**
     * Constructs the exception with a descriptive message.
     *
     * @param message Details about which Employee ID caused the duplicate.
     */
    public DuplicateProfileException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and a root cause.
     *
     * @param message Details about the duplicate profile attempt.
     * @param cause   The underlying exception that triggered this.
     */
    public DuplicateProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /**
     * Returns the error code associated with this exception.
     *
     * @return The string error code "DUPLICATE_PROFILE".
     */
    public String getErrorCode() {
        return ERROR_CODE;
    }

    /**
     * Returns a formatted string representation of this exception,
     * including the error code for easier debugging and logging.
     */
    @Override
    public String toString() {
        return "[" + ERROR_CODE + "] " + getMessage();
    }
}