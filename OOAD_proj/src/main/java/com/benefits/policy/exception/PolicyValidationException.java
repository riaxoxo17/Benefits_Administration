package com.benefits.policy.exception;

/**
 * Thrown when a BenefitPolicy fails validation in the
 * Chain of Responsibility validator chain.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * This exception has one job — represent a policy validation
 * failure with a specific error code from the failing validator.
 *
 * GRASP - Information Expert:
 * Carries the error code from the specific validator that failed,
 * giving callers precise context about what went wrong and why.
 */
public class PolicyValidationException extends Exception {

    // ── Error Code ────────────────────────────────────────────────────────────

    /**
     * The specific error code set by the validator that threw this.
     * Examples: "INVALID_SALARY_BAND_CONFIG", "WAITING_PERIOD_EXCEEDED"
     */
    private final String errorCode;

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Constructs the exception with a message and specific error code.
     *
     * @param message   Human-readable description of the validation failure.
     * @param errorCode Machine-readable code identifying the rule that failed.
     */
    public PolicyValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs the exception with a message, error code, and root cause.
     *
     * @param message   Human-readable description of the validation failure.
     * @param errorCode Machine-readable code identifying the rule that failed.
     * @param cause     The underlying exception that triggered this.
     */
    public PolicyValidationException(String message,
                                     String errorCode,
                                     Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /**
     * Returns the specific error code from the failing validator.
     *
     * @return The error code string.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns a formatted string including the error code
     * for easier debugging, logging, and tracing.
     */
    @Override
    public String toString() {
        return "[" + errorCode + "] " + getMessage();
    }
}
