package main.java.com.benefits.exceptions;

/**
 * Custom Exception: InvalidClaimRequestException
 *
 * PURPOSE:
 * This exception is thrown when a claim request contains invalid,
 * incomplete, or inconsistent data.
 *
 * It represents a validation failure at the input or business rule level.
 *
 * WHERE IT IS USED:
 * - Validation layer (e.g., ClaimValidator)
 * - Before processing a claim in the workflow
 * - When required fields are missing or incorrect
 *
 * EXAMPLES OF INVALID CASES:
 * - Missing employee ID
 * - Claim amount is negative or zero
 * - Required documents not attached
 * - Claim type not supported
 *
 * DESIGN PATTERN CONTEXT:
 * - Chain of Responsibility (Behavioral Pattern):
 *   A validation handler in the chain can throw this exception
 *   to stop further processing if input is invalid.
 *
 * GRASP PRINCIPLES:
 * - Information Expert:
 *   Validation logic (which throws this exception) resides in components
 *   that understand claim data structure.
 *
 * - Controller:
 *   This exception is typically handled by a higher-level controller/service
 *   which decides how to respond (e.g., reject request, notify user).
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for representing invalid input errors.
 *
 * - OCP (Open/Closed Principle):
 *   Additional validation-related exceptions can be added without modifying this class.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Ensures invalid inputs are explicitly handled
 *   → Prevents bad data from entering the system
 *
 * DESIGN DECISION:
 * - Separates validation errors from processing/approval errors
 * - Improves clarity in debugging and user feedback
 *
 * REAL-WORLD ANALOGY:
 * - Similar to submitting a form with missing or incorrect fields,
 *   which gets rejected before processing begins.
 */
public class InvalidClaimRequestException extends Exception {

    /**
     * Constructor to initialize the exception with a meaningful message.
     *
     * @param msg Description of what is invalid in the claim request
     *
     * WHY IMPORTANT:
     * - Helps pinpoint exactly what went wrong in the input
 * - Improves error reporting to users or logs
     * - Useful for debugging validation failures
     */
    public InvalidClaimRequestException(String msg) {
        super(msg);
    }
}