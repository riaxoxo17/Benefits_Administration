package main.java.com.benefits.enrollment.exception;

/**
 * Thrown when dependent information submitted during enrollment
 * is missing, malformed, or fails validation.
 *
 * Examples:
 * - Dependent name is null or empty
 * - Relationship type is not recognised
 * - Date of birth is missing for a dependent
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for representing invalid dependent data.
 *
 * GRASP - Information Expert:
 * Carries the specific field and reason for the validation failure.
 */
public class InvalidDependentDataException extends RuntimeException {

    /**
     * Constructs an InvalidDependentDataException with a message.
     *
     * @param message Description of what dependent data is invalid.
     */
    public InvalidDependentDataException(String message) {
        super(message);
    }
}
