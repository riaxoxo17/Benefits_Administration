package main.java.com.benefits.workflow;

/*
 * Concrete State: RejectedState
 * -----------------------------
 * PURPOSE:
 * Represents the "Rejected" state of a claim in the workflow.
 *
 * IN STATE PATTERN:
 * - Implements the State interface
 * - Encapsulates behavior specific to rejected claims
 *
 * ROLE IN SYSTEM:
 * - Final state when a claim fails validation or approval
 * - No further processing should occur after this state
 */

public class RejectedState implements State {

    /**
     * Handles behavior when claim is in REJECTED state
     *
     * FUNCTIONALITY:
     * - Indicates that the claim has been rejected
     *
     * CURRENT IMPLEMENTATION:
     * - Prints rejection message
     *
     * REAL-WORLD EXTENSION:
     * - Could trigger:
     *   → Notification to employee (email/SMS)
     *   → Logging reason for rejection
     *   → Audit trail entry
     *
     * DESIGN PATTERN:
     * - State Pattern (Behavioral)
     *
     * GRASP PRINCIPLE:
     * - Polymorphism:
     *   Behavior varies depending on current state object
     *
     * SOLID PRINCIPLE:
     * - OCP (Open/Closed Principle):
     *   New states can be added without modifying this class
     */
    public void handle() {
        System.out.println("Claim rejected");
    }

    /**
     * Returns the current status of the claim
     *
     * RETURN VALUE:
     * - "REJECTED"
     *
     * PURPOSE:
     * - Used for UI display, logging, and decision-making
     *
     * DESIGN BENEFIT:
     * - Status logic is encapsulated within the state itself
     * - Avoids centralized conditional logic
     */
    public String getStatus() {
        return "REJECTED";
    }
}