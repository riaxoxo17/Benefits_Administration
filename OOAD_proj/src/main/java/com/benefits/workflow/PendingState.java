package main.java.com.benefits.workflow;

/*
 * Concrete State: PendingState
 * ----------------------------
 * PURPOSE:
 * Represents the "Pending" state of a claim in the workflow.
 *
 * IN STATE PATTERN:
 * - Implements the State interface
 * - Defines behavior specific to the "PENDING" condition
 *
 * ROLE IN SYSTEM:
 * - Typically the initial state when a claim is created
 * - Awaiting validation, approval, or further processing
 */

public class PendingState implements State {

    /**
     * Handles behavior when claim is in PENDING state
     *
     * FUNCTIONALITY:
     * - Indicates that the claim is awaiting approval
     *
     * CURRENT IMPLEMENTATION:
     * - Prints a status message
     *
     * REAL-WORLD EXTENSION:
     * - Could trigger:
     *   → Validation checks
     *   → Assignment to approver
     *   → Notification to manager
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
        System.out.println("Claim is pending approval...");
    }

    /**
     * Returns the current status of the claim
     *
     * RETURN VALUE:
     * - "PENDING"
     *
     * PURPOSE:
     * - Used for UI display, logging, and decision-making
     *
     * DESIGN BENEFIT:
     * - Each state defines its own status → avoids centralized condition checks
     */
    public String getStatus() {
        return "PENDING";
    }
}