package com.benefits.exception;

/**
 * Thrown when an employee profile cannot be found in the system.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This exception has one job — represent a "profile not found" error.
 *
 * GRASP - Information Expert:
 * Carries all relevant information about the failure so the caller
 * can respond appropriately without guessing what went wrong.
 */
public class ProfileNotFoundException extends Exception {

    // ── Error Code ───────────────────────────────────────────────────────────

    /**
     * Unique error code for this exception type.
     * Useful for logging, monitoring, and API error responses.
     */
    private static final String ERROR_CODE = "PROFILE_NOT_FOUND";

    // ── Constructors ─────────────────────────────────────────────────────────

    /**
     * Constructs the exception with a descriptive message.
     *
     * @param message Details about which profile was not found and why.
     */
    public ProfileNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and a root cause.
     *
     * @param message Details about which profile was not found.
     * @param cause   The underlying exception that triggered this.
     */
    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /**
     * Returns the error code associated with this exception.
     *
     * @return The string error code "PROFILE_NOT_FOUND".
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