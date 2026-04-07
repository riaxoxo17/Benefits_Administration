package main.java.com.benefits.exceptions;

/**
 * Custom Exception: ApprovalTimeoutException
 *
 * PURPOSE:
 * This exception is thrown when an approval process exceeds the allowed time limit.
 * It represents a failure due to delay in decision-making rather than rejection.
 *
 * WHERE IT IS USED:
 * - In approval workflows where time-bound decisions are required
 * - Example: If a manager or system does not approve within a deadline
 *
 * DESIGN PATTERN CONTEXT:
 * - Chain of Responsibility (Behavioral Pattern):
 *   If an approval step takes too long, this exception can terminate the chain
 *   and prevent further processing.
 *
 * GRASP PRINCIPLE:
 * - Low Coupling:
 *   The exception is independent of business logic, UI, and persistence layers.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for representing a timeout condition.
 *
 * - OCP (Open/Closed Principle):
 *   New exception types (e.g., EscalationException) can be added without
 *   modifying existing exception classes.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Forces calling methods to handle timeout explicitly
 *   → Ensures delays are not silently ignored
 *
 * REAL-WORLD ANALOGY:
 * - Similar to an approval request expiring because a manager didn’t respond in time
 */
public class ApprovalTimeoutException extends Exception {

    /**
     * Constructor to initialize the exception with a meaningful message.
     *
     * @param msg Description of why the timeout occurred
     *
     * WHY IMPORTANT:
     * - Helps in debugging time-related failures
     * - Enables better logging and monitoring
     * - Can be shown to users or used for escalation workflows
     */
    public ApprovalTimeoutException(String msg) {
        super(msg);
    }
}