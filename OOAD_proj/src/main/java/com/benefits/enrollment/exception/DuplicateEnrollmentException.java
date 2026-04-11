package main.java.com.benefits.enrollment.exception;

/**
 * Thrown when an employee tries to enroll in a plan they are
 * already enrolled in.
 *
 * The system must prevent duplicate enrollments for the same
 * employee + plan combination to avoid double-billing in payroll.
 *
 * SOLID - Single Responsibility Principle (SRP):
 * Solely responsible for representing a duplicate enrollment attempt.
 *
 * GRASP - Information Expert:
 * Carries the employee ID and plan ID that caused the conflict.
 */
public class DuplicateEnrollmentException extends RuntimeException {

    private final String employeeId;
    private final String planId;

    /**
     * Constructs a DuplicateEnrollmentException.
     *
     * @param employeeId The employee who is already enrolled.
     * @param planId     The plan they are already enrolled in.
     */
    public DuplicateEnrollmentException(String employeeId, String planId) {
        super("Employee [" + employeeId + "] is already enrolled in Plan ["
                + planId + "]. Duplicate enrollment not allowed.");
        this.employeeId = employeeId;
        this.planId = planId;
    }

    /**
     * Returns the employee ID involved in the duplicate.
     *
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Returns the plan ID involved in the duplicate.
     *
     * @return The plan ID.
     */
    public String getPlanId() {
        return planId;
    }
}
