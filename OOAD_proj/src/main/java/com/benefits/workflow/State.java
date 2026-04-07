package main.java.com.benefits.workflow;

/*
 * Behavioural Pattern: State
 * ---------------------------
 * PURPOSE:
 * This interface defines the contract for all possible states
 * in the claim workflow.
 *
 * IN STATE PATTERN:
 * - State → This interface
 * - Concrete States → PendingState, ApprovedState, RejectedState
 * - Context → ClaimContext
 *
 * WHY IMPORTANT?
 * - Ensures all states implement consistent behavior
 * - Enables polymorphism (different behavior for different states)
 * - Eliminates need for conditional logic (if-else / switch)
 *
 * GRASP PRINCIPLES:
 * - Polymorphism:
 *   Different states provide their own implementation of behavior
 *
 * - Protected Variations:
 *   Changes in state behavior do not affect the rest of the system
 *
 * SOLID PRINCIPLES:
 * - OCP (Open/Closed Principle):
 *   New states can be added without modifying existing code
 *
 * - DIP (Dependency Inversion Principle):
 *   ClaimContext depends on this abstraction, not concrete states
 */

public interface State {

    /**
     * Defines the action to be performed in a given state
     *
     * PURPOSE:
     * - Encapsulates state-specific behavior
     *
     * EXAMPLES:
     * - PendingState → "Claim is pending approval..."
     * - ApprovedState → "Claim approved"
     * - RejectedState → "Claim rejected"
     *
     * DESIGN BENEFIT:
     * - Behavior varies dynamically based on current state object
     */
    void handle();

    /**
     * Returns the current status of the state
     *
     * PURPOSE:
     * - Provides a consistent way to retrieve state information
     *
     * RETURN VALUE:
     * - String representation of the state
     *   (e.g., "PENDING", "APPROVED", "REJECTED")
     *
     * DESIGN BENEFIT:
     * - Each state controls its own status representation
     * - Avoids centralized logic for status management
     */
    String getStatus();
}