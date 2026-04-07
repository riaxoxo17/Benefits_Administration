package main.java.com.benefits.exceptions;

/**
 * Custom Exception: ClaimProcessingException
 *
 * PURPOSE:
 * This exception represents a general failure during the claim processing workflow.
 * It acts as a higher-level abstraction for errors that occur while handling claims.
 *
 * WHERE IT IS USED:
 * - In the claim processing pipeline (e.g., validation, approval, settlement)
 * - When a generic or unexpected issue occurs that is not covered by
 *   more specific exceptions (e.g., ApprovalRejectedException, ApprovalTimeoutException)
 *
 * DESIGN PATTERN CONTEXT:
 * - Chain of Responsibility (Behavioral Pattern):
 *   If any handler in the claim processing chain encounters an unrecoverable error,
 *   this exception can be thrown to terminate the flow.
 *
 * GRASP PRINCIPLES:
 * - Controller:
 *   This exception is typically propagated up to a controller/service layer
 *   which decides how to handle or report the error.
 *
 * - Low Coupling:
 *   The exception is independent of specific modules and can be reused across
 *   different parts of the system.
 *
 * SOLID PRINCIPLES:
 * - SRP (Single Responsibility Principle):
 *   This class is only responsible for representing claim processing failures.
 *
 * - OCP (Open/Closed Principle):
 *   Allows extension by introducing more specific exceptions without modifying this class.
 *
 * EXCEPTION HANDLING STRATEGY:
 * - This is a checked exception (extends Exception)
 *   → Ensures that claim-related failures are explicitly handled
 *   → Encourages structured error propagation across layers
 *
 * DESIGN DECISION:
 * - Acts as a "base-level business exception" for claims
 * - Can be used as a wrapper for lower-level exceptions if needed
 *
 * REAL-WORLD ANALOGY:
 * - Similar to a "processing failed" status in insurance systems when something
 *   goes wrong during claim handling but the exact cause may vary.
 */
public class ClaimProcessingException extends Exception {

    /**
     * Constructor to initialize the exception with a descriptive message.
     *
     * @param msg Explanation of the processing failure
     *
     * WHY IMPORTANT:
     * - Helps identify where and why the failure occurred
     * - Improves debugging and logging
     * - Can be surfaced to higher layers for user feedback or retry logic
     */
    public ClaimProcessingException(String msg) {
        super(msg);
    }
}