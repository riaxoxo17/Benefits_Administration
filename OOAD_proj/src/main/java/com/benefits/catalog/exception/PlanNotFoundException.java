package main.java.com.benefits.catalog.exception;

/**
 * Thrown when a requested benefit plan cannot be found in the system.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This exception has one job — represent a "plan not found" error.
 * Clean, focused, and immediately understandable by any developer.
 *
 * GRASP - Information Expert:
 * Carries all relevant context about the failure so the caller
 * can respond appropriately without guessing what went wrong.
 *
 * Mirrors the structure of ProfileNotFoundException in Component 1
 * for consistency across the entire subsystem.
 */
public class PlanNotFoundException extends Exception {

    // ── Error Code ────────────────────────────────────────────────────────────

    /**
     * Unique error code for this exception type.
     * Matches the exception table defined in the project specification.
     */
    private static final String ERROR_CODE = "PLAN_NOT_FOUND";

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Constructs the exception with a descriptive message.
     *
     * @param message Details about which plan was not found and why.
     */
    public PlanNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and a root cause.
     *
     * @param message Details about which plan was not found.
     * @param cause   The underlying exception that triggered this.
     */
    public PlanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /**
     * Returns the error code associated with this exception.
     *
     * @return The string error code "PLAN_NOT_FOUND".
     */
    public String getErrorCode() {
        return ERROR_CODE;
    }

    /**
     * Returns a formatted string including the error code
     * for easier debugging, logging, and tracing.
     */
    @Override
    public String toString() {
        return "[" + ERROR_CODE + "] " + getMessage();
    }
}
