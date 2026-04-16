package main.java.com.benefits.eligibility.exception;

/**
 * Thrown when the eligibility verification process itself fails
 * due to a system error (e.g. profile not found, plan not found,
 * or an unexpected internal error during the check).
 *
 * This is different from NotEligibleForPlanException — that means
 * the employee simply does not qualify. This means the CHECK itself
 * could not be completed.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for representing a failed eligibility check.
 *
 * GRASP - Information Expert:
 * Carries the reason why the check failed as a message.
 */
public class EligibilityCheckFailedException extends RuntimeException {

    /**
     * Constructs a new EligibilityCheckFailedException with a message.
     *
     * @param message Description of why the eligibility check failed.
     */
    public EligibilityCheckFailedException(String message) {
        super(message);
    }

    /**
     * Constructs a new EligibilityCheckFailedException with a message
     * and the underlying cause.
     *
     * @param message Description of why the eligibility check failed.
     * @param cause   The underlying exception that caused this failure.
     */
    public EligibilityCheckFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
