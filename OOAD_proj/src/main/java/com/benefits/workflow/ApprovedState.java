package main.java.com.benefits.workflow;

/*
 * State Pattern implementation
 * ----------------------------
 * PURPOSE:
 * This class represents the "Approved" state of a claim in the system.
 *
 * In the State Pattern:
 * - State → Interface (State)
 * - Concrete State → ApprovedState (this class)
 * - Context → Claim (or workflow manager that uses this state)
 *
 * WHY STATE PATTERN?
 * - Allows an object to change its behavior when its internal state changes
 * - Eliminates complex conditional logic (if-else / switch)
 * - Makes state transitions modular and maintainable
 */

public class ApprovedState implements State {

    /**
     * Handles behavior when claim is in APPROVED state
     *
     * FUNCTIONALITY:
     * - Executes actions specific to approved claims
     *
     * CURRENT IMPLEMENTATION:
     * - Prints confirmation message
     *
     * REAL-WORLD EXTENSION:
     * - Could trigger:
     *   → Payroll processing
     *   → Notification to employee
     *   → Record updates in database
     *
     * GRASP PRINCIPLE:
     * - Polymorphism:
     *   Behavior changes based on state object instead of condition checks
     *
     * SOLID PRINCIPLE:
     * - OCP (Open/Closed Principle):
     *   New states can be added without modifying existing ones
     */
    public void handle() {
        System.out.println("Claim approved");
    }

    /**
     * Returns the current state as a string
     *
     * PURPOSE:
     * - Helps in tracking or displaying claim status
     *
     * RETURN VALUE:
     * - "APPROVED"
     *
     * DESIGN NOTE:
     * - Keeps status representation consistent across system
     */
    public String getStatus() {
        return "APPROVED";
    }
}