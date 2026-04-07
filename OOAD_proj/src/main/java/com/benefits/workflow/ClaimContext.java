package main.java.com.benefits.workflow;

/*
 * Context Class in State Pattern
 * ------------------------------
 * PURPOSE:
 * This class maintains the current state of a claim and delegates
 * behavior to the corresponding State object.
 *
 * IN STATE PATTERN:
 * - Context → ClaimContext (this class)
 * - State → State interface
 * - Concrete States → ApprovedState, RejectedState, etc.
 *
 * WHY IMPORTANT?
 * - Removes the need for conditional logic (if-else/switch)
 * - Enables dynamic behavior based on current state
 */

public class ClaimContext {

    /**
     * Holds the current state of the claim
     *
     * TYPE:
     * - State interface → allows polymorphism
     *
     * DESIGN BENEFIT:
     * - Can switch between different states at runtime
     */
    private State currentState;

    /**
     * Constructor to initialize context with a specific state
     *
     * @param state → initial state of the claim
     *
     * USE CASE:
     * - When a claim is first created (e.g., PendingState)
     *
     * SOLID PRINCIPLE:
     * - DIP (Dependency Inversion Principle):
     *   Depends on abstraction (State), not concrete implementations
     */
    public ClaimContext(State state) {
        this.currentState = state;
    }

    /**
     * Updates the current state of the claim
     *
     * @param state → new state to transition into
     *
     * PURPOSE:
     * - Enables dynamic state transitions (e.g., Pending → Approved)
     *
     * DESIGN BENEFIT:
     * - No need to modify existing logic when adding new states
     *
     * GRASP PRINCIPLE:
     * - Indirection:
     *   State transitions are handled through this context instead of directly
     */
    public void setState(State state) {
        this.currentState = state;
    }

    /**
     * Executes behavior based on current state
     *
     * PROCESS:
     * - Delegates execution to current state's handle() method
     *
     * EXAMPLE:
     * - ApprovedState → prints "Claim approved"
     * - RejectedState → prints rejection message
     *
     * DESIGN PATTERN:
     * - Polymorphism:
     *   Behavior changes depending on state object
     *
     * BENEFIT:
     * - Avoids complex conditionals
     */
    public void process() {
        currentState.handle();
    }

    /**
     * Retrieves the current status of the claim
     *
     * RETURNS:
     * - String representation of state (e.g., "APPROVED", "PENDING")
     *
     * PURPOSE:
     * - Used for display, logging, or decision-making
     *
     * DESIGN BENEFIT:
     * - Status logic is encapsulated within each state class
     */
    public String getStatus() {
        return currentState.getStatus();
    }
}