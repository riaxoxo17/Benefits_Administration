package main.java.com.benefits.eligibility.exception;

/**
 * Thrown when an employee is found but does NOT meet the eligibility
 * criteria for a specific benefit plan.
 *
 * This is a business rule violation — the check ran successfully
 * but the result is: NOT ELIGIBLE.
 *
 * Examples:
 * - A CONTRACT employee trying to enroll in a FULL_TIME-only plan
 * - An employee in salary band B1 trying to enroll in a B3+ plan
 * - An employee who joined 2 months ago trying to enroll before
 *   the 6-month waiting period ends
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for representing an ineligibility outcome.
 *
 * GRASP - Information Expert:
 * Carries the employee ID, plan ID, and the reason for ineligibility.
 */
public class NotEligibleForPlanException extends RuntimeException {

    private final String employeeId;
    private final String planId;

    /**
     * Constructs a NotEligibleForPlanException.
     *
     * @param employeeId The ID of the employee who failed eligibility.
     * @param planId     The ID of the plan they are ineligible for.
     * @param reason     The specific reason they are not eligible.
     */
    public NotEligibleForPlanException(String employeeId,
                                       String planId,
                                       String reason) {
        super("Employee [" + employeeId + "] is NOT eligible for Plan ["
                + planId + "]. Reason: " + reason);
        this.employeeId = employeeId;
        this.planId = planId;
    }

    /**
     * Returns the employee ID that failed the eligibility check.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Returns the plan ID for which the employee is not eligible.
     *
     * @return The plan ID.
     */
    public String getPlanId() {
        return planId;
    }
}
