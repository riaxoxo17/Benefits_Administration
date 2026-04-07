package main.java.com.benefits.exceptions;

/**
 * Custom Exception: ApprovalRejectedException
 *
 * PURPOSE:
 * This exception is thrown when a claim or request fails the approval process.
 * It represents a business rule violation rather than a system failure.
 *
 * WHERE IT IS USED:
 * - Typically used in approval workflows (e.g., Claim Approval Handler)
 * - Thrown when conditions for approval are not satisfied
 *
 * DESIGN PATTERN CONTEXT:
 * - Used in Chain of Responsibility (Behavioral Pattern):
 *   If an approval handler rejects a request, this exception can be thrown
 *   to stop further processing in the chain.
 *
 * GRASP PRINCIPLE:
 * - Low Coupling:
 *   Business logic layers can throw this exception without depending
 *   on specific UI or persistence layers.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for representing an approval rejection error.
 *
 * - OCP (Open/Closed Principle):
 *   New types of exceptions can be added without modifying existing code.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Forces the caller to explicitly handle or declare it
 *   → Ensures approval failures are not ignored silently
 */
public class ApprovalRejectedException extends Exception {

    /**
     * Constructor to initialize exception with a meaningful message.
     *
     * @param msg Detailed reason for rejection
     *
     * WHY IMPORTANT:
     * - Helps in debugging
     * - Improves logging clarity
     * - Useful for user-facing error messages
     */
    public ApprovalRejectedException(String msg) {
        super(msg);
    }
}